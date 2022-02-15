package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.AddDiaryBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerJournalComponent;
import com.phjt.shangxueyuan.mvp.contract.JournalContract;
import com.phjt.shangxueyuan.mvp.presenter.JournalPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.NotesEditingAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Created by Template
 *
 * @author :
 * description :发表日记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */
public class JournalActivity extends BaseActivity<JournalPresenter> implements JournalContract.View, TextWatcher, FileUploadUtils.UploadCallback {

    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.tv_text_theme)
    TextView tvTextTheme;
    @BindView(R.id.tv_details_commencement)
    TextView tvDetailsCommencement;
    @BindView(R.id.tv_theme_title)
    TextView tvThemeTitle;
    @BindView(R.id.tv_activity_time)
    TextView tvActivityTime;
    @BindView(R.id.tv_activity_content)
    TextView tvActivityContent;
    @BindView(R.id.edit_feedback)
    EditText editDiary;
    @BindView(R.id.tv_input_num)
    TextView tvInputNum;
    @BindView(R.id.fl_edit_feedback)
    FrameLayout flEditFeedback;
    @BindView(R.id.recycle_picture)
    RecyclerView recyclePicture;
    private Drawable ivUp, ivDown;

    private int addPosition;
    NotesEditingAdapter feedbackAdapter;
    private List<FeedbackPictureBean> pictureList;
    private int mMaxSelectNum = 9;
    private final int MAX_TEXT_NUMBER = 10000;
    private final int MIN_TEXT_NUMBER = 5;
    private boolean mPermissions = false;
    private String diaryId = "";
    private String punchCardId = "";
    private String calendarDate = "";
    private String motifTitle = "";
    private String diaryContent = "";
    private String nodeTaskLinkId = "";
    private String trainingCampId = "";
    private String motifId = "";
    private int reissueCardType;
    /**
     * 是否再次发布
     */
    private int onceMore;
    private List<String> mDatasImgList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerJournalComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_journal;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("发表日记");
        tvCommonRight.setText("发表");
        tvCommonRight.setVisibility(View.VISIBLE);
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color_1074FF));
        ivUp = ContextCompat.getDrawable(this, R.drawable.arrow_catalog_up);
        ivDown = ContextCompat.getDrawable(this, R.drawable.arrow_catalog_down);
        editDiary.addTextChangedListener(this);
        getIntents();
        pictureList = new ArrayList<>();
        mDatasImgList = new ArrayList<>();
        if (pictureList.size() == 0) {
            pictureList.add(new FeedbackPictureBean());
        }
        feedbackAdapter = new NotesEditingAdapter(pictureList);
        feedbackAdapter.bindToRecyclerView(recyclePicture);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        recyclePicture.setLayoutManager(layoutManager);
        setHttpData();
        setOnItemChildClick();
        setsetOnItemClick();
    }


    /**
     * 获取传递数据
     */
    private void getIntents() {
        reissueCardType = getIntent().getIntExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, 0);
        diaryId = getIntent().getStringExtra(Constant.BUNDLE_ADD_DIARY_ID);
        nodeTaskLinkId = getIntent().getStringExtra(Constant.BUNDLE_ADD_NODE_TASK_LINK_ID);
        trainingCampId = getIntent().getStringExtra(Constant.BUNDLE_ADD_TRAINING_CAMP_ID);
        punchCardId = getIntent().getStringExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID);
        motifId = getIntent().getStringExtra(Constant.BUNDLE_ADD_MOTIF_ID);
        calendarDate = getIntent().getStringExtra(Constant.BUNDLE_ADD_CALENDAR_DATE);
        onceMore = getIntent().getIntExtra(Constant.BUNDLE_ADD_ONCE_MORE, 0);
    }


    /**
     * 请求网络数据
     */
    private void setHttpData() {
        //获取主题
        if (mPresenter != null && !TextUtils.isEmpty(motifId) && !TextUtils.isEmpty(punchCardId) && !"0".equals(motifId) && !"0".equals(punchCardId)) {
            mPresenter.getMotifDetails(motifId, punchCardId);
        }
        //获取日记详情
        if (mPresenter != null && !TextUtils.isEmpty(diaryId) && onceMore == 1 && !"0".equals(diaryId)) {
            mPresenter.getDiaryDetails(diaryId);
        }
    }

    private void setOnItemChildClick() {
        feedbackAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_add) {
                //添加图片
                uploadImg();
            }

            if (view.getId() == R.id.iv_delete) {
                //删除图片
                if (pictureList.size() == Constant.MAX_PICTURE_NUMBER && !TextUtils.isEmpty(pictureList.get(pictureList.size() - 1).getUrlPath())) {
                    adapter.remove(position);
                    adapter.addData(new FeedbackPictureBean());
                } else {
                    adapter.remove(position);
                }
                mDatasImgList.remove(position);
                mMaxSelectNum = 9 - mDatasImgList.size();
                addPosition = mDatasImgList.size();
                if (addPosition == Constant.MAX_PICTURE_NUMBER) {
                    FeedbackPictureBean picture = new FeedbackPictureBean();
                    picture.setUrlPath("");
                    picture.setAbsolutePath("");
                    pictureList.add(picture);
                    feedbackAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void setsetOnItemClick() {
        feedbackAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mDatasImgList.size() > 0) {
                Intent intent = new Intent(JournalActivity.this, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) mDatasImgList);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            }
        });
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


    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_common_right, R.id.tv_details_commencement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                SoftKeyboardUtil.hideSoftKeyboard(this);
                break;
            case R.id.tv_common_right:
                doSubmit();
                break;
            case R.id.tv_details_commencement:
                if (!TextUtils.isEmpty(tvActivityContent.getText().toString()) && "展开详情".equals(tvDetailsCommencement.getText().toString())) {
                    tvDetailsCommencement.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ivUp, null);
                    tvActivityContent.setVisibility(View.VISIBLE);
                    tvDetailsCommencement.setText("收起详情");
                } else if ("收起详情".equals(tvDetailsCommencement.getText().toString())) {
                    tvDetailsCommencement.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ivDown, null);
                    tvActivityContent.setVisibility(View.GONE);
                    tvDetailsCommencement.setText("展开详情");
                }
                break;
            default:
                break;
        }
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
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    if (mPermissions) {
                        setCancelSureDialog();
                    } else {
                        mPermissions = true;
                    }
                } else {
                    //权限请求失败，但未选中“不再提示”选项
                    mPermissions = false;
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
            setmImg();
        }
    }

    private void setCancelSureDialog() {
        DialogUtils.showCancelSureDialog(this, "无法使用相册",
                "可以到手机系统“设置”中开启？", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                });
    }


    /**
     * 调用相机或者相册
     */
    private void setmImg() {
        PictureSelector.create(JournalActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .isCamera(true)
                .maxSelectNum(mMaxSelectNum)
                .selectionMode(PictureConfig.MULTIPLE)
                .isEnableCrop(false)
                .circleDimmedLayer(false)
                .showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        // 图片、视频、音频选择结果回调
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (!selectList.isEmpty()) {
                LocalMedia localMedia = null;
                for (int i = 0; i < selectList.size(); i++) {
                    localMedia = selectList.get(i);
                    File file = new File(localMedia.getCompressPath());
                    if (!file.exists()) {
                        return;
                    }
                    try {
                        FileUploadUtils.upload(this, file, 0, this);
                    } catch (Exception e) {
                        LogUtils.e("=====================图片上传异常" + e.toString());
                    }
                }
            }

        }
    }


    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.realUrl)) {
            TipsUtil.show("图片上传失败");
            return;
        }
        mDatasImgList.add(bean.realUrl);
        mMaxSelectNum = 9 - mDatasImgList.size();
        addPosition = mDatasImgList.size();
        if (addPosition == Constant.MAX_PICTURE_NUMBER) {
            pictureList.get(addPosition - 1).setUrlPath(bean.url);
            pictureList.get(addPosition - 1).setAbsolutePath(bean.realUrl);
            feedbackAdapter.notifyItemChanged(addPosition - 1);
        } else {
            FeedbackPictureBean picture = new FeedbackPictureBean();
            picture.setUrlPath(bean.url);
            picture.setAbsolutePath(bean.realUrl);
            pictureList.add(addPosition - 1, picture);
            feedbackAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFileUploadError(Throwable e) {
        TipsUtil.show("图片上传失败");
    }

    /**
     * 提交发布日记
     */
    private void doSubmit() {
        String strEditDiary = editDiary.getText().toString().replaceAll(" ", "");
        if (strEditDiary.length() <= 0) {
            TipsUtil.show("请按照要求完成日记内容");
            return;
        }
        if (strEditDiary.length() < MIN_TEXT_NUMBER) {
            TipsUtil.show("请按照要求完成日记内容");
            return;
        }
        if (strEditDiary.length() > MAX_TEXT_NUMBER) {
            // 文字超过上限
            TipsUtil.show("您的输入已超出上限！");
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
        //如果第一次发布 diaryId赋值为空
        if (onceMore == 0) {
            diaryId="";
        }else {
            diaryId = getIntent().getStringExtra(Constant.BUNDLE_ADD_DIARY_ID);
        }
        if (mPresenter != null) {
            mPresenter.addDiary(diaryId, punchCardId, strEditDiary, reissueCardType, img.toString(), calendarDate,
                    nodeTaskLinkId, trainingCampId, motifId);
        }
    }

    @Override
    public void addDiarySuccess(BaseBean<AddDiaryBean> addDiaryBean) {
        finish();
        Intent intent = new Intent(this, MyWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "日记详情");
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_DIARY_DETAILS) +
                "?id=" + addDiaryBean.data.getId() + "&cardId=" + punchCardId);
        intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, addDiaryBean.data.getId());
        intent.putExtra(Constant.PARTICIPATION_PUNCH_CARDSID, punchCardId);
        intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, trainingCampId);
        intent.putExtra("localType", "1111");
        startActivity(intent);
        EventBusManager.getInstance().post(new EventBean(EventBean.ADD_DIARY, null));
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    @Override
    public void getMotifDetails(MotifDetailBean bean) {
        if (!TextUtils.isEmpty(bean.getMotifTitle())) {
            tvTextTheme.setVisibility(View.VISIBLE);
            tvThemeTitle.setVisibility(View.VISIBLE);
            tvActivityTime.setVisibility(View.VISIBLE);
        }
        tvThemeTitle.setText(bean.getMotifTitle());
        tvActivityTime.setText(bean.getMotifDate());
        tvActivityContent.setText(bean.getMotifDescribe());
    }

    @Override
    public void getDiaryDetails(MyDiaryBean bean) {
        pictureList.clear();
        if (!TextUtils.isEmpty(bean.getDiaryImg())) {
            List<String> imgList = Arrays.asList(bean.getDiaryImg().split(","));
            for (int i = 0; i < imgList.size(); i++) {
                FeedbackPictureBean picture = new FeedbackPictureBean();
                picture.setAbsolutePath(String.valueOf(imgList.get(i)));
                pictureList.add(picture);
            }
            for (int i = 0; i < pictureList.size(); i++) {
                mDatasImgList.add(pictureList.get(i).getAbsolutePath());
            }
            mMaxSelectNum = 9 - pictureList.size();
        }

        if (pictureList.size() < Constant.MAX_PICTURE_NUMBER) {
            FeedbackPictureBean picture = new FeedbackPictureBean();
            picture.setAbsolutePath("");
            pictureList.add(picture);
            feedbackAdapter.notifyDataSetChanged();
        }
        feedbackAdapter.setNewData(pictureList);
        if (!TextUtils.isEmpty(bean.getContent())) {
            editDiary.setText(bean.getContent());
            editDiary.setSelection(bean.getContent().length());
        }
    }

    @Override
    public void diaryDetailsFailed() {

    }

    @Override
    public void showLoading() {
        if (tvCommonRight != null) {
            tvCommonRight.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (tvCommonRight != null) {
            tvCommonRight.setClickable(true);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.show(message);
    }


    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
