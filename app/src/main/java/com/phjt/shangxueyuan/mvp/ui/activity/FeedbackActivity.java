package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.di.component.DaggerFeedbackComponent;
import com.phjt.shangxueyuan.mvp.contract.FeedbackContract;
import com.phjt.shangxueyuan.mvp.presenter.FeedbackPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.FeedbackAdapter;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author: Roy
 * date:    05/06/2020 18:56
 * company: ????????????
 * dedication: ????????????
 */
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract.View, TextWatcher, FileUploadUtils.UploadCallback {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.edit_feedback)
    EditText etFeedback;
    @BindView(R.id.tv_input_num)
    TextView tvInputNum;
    @BindView(R.id.recycle_picture)
    RecyclerView recyclePicture;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private int addPosition;
    FeedbackAdapter feedbackAdapter;
    private List<FeedbackPictureBean> pictureList = new ArrayList<>();

    private static final int MAX_PICTURE_NUMBER = 3;
    private static final int MAX_TEXT_NUMBER = 5000;
    private boolean mPermissions = false;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //??????????????????,?????????????????????
        DaggerFeedbackComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("????????????");
        etFeedback.addTextChangedListener(this);

        if (pictureList.size() == 0) {
            pictureList.add(new FeedbackPictureBean());
        }
        feedbackAdapter = new FeedbackAdapter(pictureList);
        feedbackAdapter.bindToRecyclerView(recyclePicture);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclePicture.setLayoutManager(layoutManager);
        feedbackAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                 if(view.getId() == R.id.iv_add) { //????????????
                    addPosition = position;
                    uploadImg();
                }

                if (view.getId() == R.id.iv_delete) { //????????????
                    if (pictureList.size() == MAX_PICTURE_NUMBER
                            && !TextUtils.isEmpty(pictureList.get(pictureList.size() - 1).getUrlPath())) {
                        adapter.remove(position);
                        adapter.addData(new FeedbackPictureBean());
                    } else {
                        adapter.remove(position);
                    }
                }
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View ?????????????????? ??????????????? ????????? P ??? ?????? ?????????
     * Demo
     *
     * @param intent {@code intent} ????????? {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                // ???????????????
                finish();
                break;
            case R.id.tv_submit:
                // ????????????
                doSubmit();
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_FEEDBACK_SUBMIT);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.toString().replaceAll(" ", "").length();
        tvInputNum.setText(length + "/" + MAX_TEXT_NUMBER);
        tvInputNum.setTextColor(ContextCompat.getColor(this,
                length > MAX_TEXT_NUMBER ? R.color.colorTintRed : R.color.color_333333));
    }

    @SuppressLint("CheckResult")
    private void uploadImg() {
        new RxPermissions(this).request(Manifest.permission.CAMERA,
                WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        setmImg();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //????????????????????????????????????????????????shouldShowRequestPermissionRationale??????false???
                // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //?????????????????????????????????????????????????????????
                    if(mPermissions) {
                        setCancelSureDialog();
                    }else {
                        mPermissions=true;
                    }
                } else {
                    //?????????????????????????????????????????????????????????
                    mPermissions=false;
                }
                break;
            }
        }
        if (hasAllGranted) {
            //??????????????????
            setmImg();
        }
    }

    private void setCancelSureDialog() {
        DialogUtils.showCancelSureDialog(this, "??????????????????",
                "?????????????????????????????????????????????", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        //????????????????????????????????????
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
    }
    /**
     * ????????????????????????
     */
    private void setmImg() {
        PictureSelector.create(FeedbackActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .isCamera(true)
                .selectionMode(PictureConfig.SINGLE)
                .isEnableCrop(false)
                .circleDimmedLayer(false)
                .showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // ??????????????????????????????????????????
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (!selectList.isEmpty()) {
                    LocalMedia localMedia = selectList.get(0);
                    File file = new File(localMedia.getCompressPath());
                    if (!file.exists()) {
                        return;
                    }
                    FileUploadUtils.upload(this, file, 0, this);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.realUrl)) {
            TipsUtil.show("??????????????????");
            return;
        }

        if (addPosition == MAX_PICTURE_NUMBER - 1) {
            pictureList.get(addPosition).setUrlPath(bean.url);
            pictureList.get(addPosition).setAbsolutePath(bean.realUrl);
            feedbackAdapter.notifyItemChanged(addPosition);
        } else {
            FeedbackPictureBean picture = new FeedbackPictureBean();
            picture.setUrlPath(bean.url);
            picture.setAbsolutePath(bean.realUrl);
            pictureList.add(addPosition, picture);
            feedbackAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFileUploadError(Throwable e) {
        TipsUtil.show("??????????????????");
    }


    private void doSubmit() {
        String content = etFeedback.getText().toString().replaceAll(" ", "");
        if (content.length() <= 0) {
            TipsUtil.show("?????????????????????");
            return;
        }
        if (content.length() > MAX_TEXT_NUMBER) {
            // ??????????????????
            TipsUtil.show("??????????????????????????????");
            return;
        }

        StringBuilder img = new StringBuilder();
        if (pictureList.size() > 0) {
            for (FeedbackPictureBean bean : pictureList) {
                if (!TextUtils.isEmpty(bean.getAbsolutePath())) {
                    img.append(bean.getAbsolutePath()).append(",");
                }
            }

            if (img.length() > 0) {
                img.replace(img.length() - 1, img.length(), "");
            }
        }

        if (mPresenter != null) {
            mPresenter.submitFeedback(content, img.toString());
            tvSubmit.setEnabled(false);
        }
    }

    @Override
    public void submitSuccess() {
        TipsUtil.show("????????????????????????");
        finish();
    }

    @Override
    public void submitFailed(String msg) {
        TipsUtil.show(msg);
        tvSubmit.setEnabled(true);
    }
}
