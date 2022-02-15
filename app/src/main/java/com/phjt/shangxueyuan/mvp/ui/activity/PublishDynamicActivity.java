package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
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
import com.phjt.shangxueyuan.di.component.DaggerPublishDynamicComponent;
import com.phjt.shangxueyuan.mvp.contract.PublishDynamicContract;
import com.phjt.shangxueyuan.mvp.presenter.PublishDynamicPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.NotesEditingAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.FileSizeUtil;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.LoadingDialog;
import com.phjt.shangxueyuan.utils.StringUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
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
 * @author: Created by GengYan
 * date: 11/04/2020 16:18
 * company: 普华集团
 * description: 发布动态
 */
public class PublishDynamicActivity extends BaseActivity<PublishDynamicPresenter> implements PublishDynamicContract.View,
        TextWatcher, FileUploadUtils.UploadCallback {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView mTvCommonRight;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_content_count)
    TextView mTvContentCount;
    @BindView(R.id.tv_label_content)
    TextView mTvLabelContent;
    @BindView(R.id.iv_topic)
    ImageView ivTopic;
    @BindView(R.id.rv_publish)
    RecyclerView mRvPublish;
    private String topicId;
    private String topicName;
    private int topicType;

    private NotesEditingAdapter mAdapter;
    /**
     * 选择的图片集合
     */
    private List<LocalMedia> mSelectList;
    /**
     * 当前上传的顺序
     */
    private int mIndex = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPublishDynamicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (null != getIntent().getStringExtra(Constant.PUSH_TOPICID)) {
            topicType = 1;
            topicName = getIntent().getStringExtra(Constant.PUSH_TOPICNAME);
            topicId = getIntent().getStringExtra(Constant.PUSH_TOPICID);
            mTvLabelContent.setText(topicName);
        }
        mTvCommonTitle.setText("动态发布");
        mTvCommonRight.setVisibility(View.VISIBLE);
        mTvCommonRight.setText("发布");
        mTvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color_1074FF));
        mEtContent.addTextChangedListener(this);
        mEtContent.requestFocus();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        mRvPublish.setLayoutManager(layoutManager);
        List<FeedbackPictureBean> pictureList = new ArrayList<>();
        pictureList.add(new FeedbackPictureBean());
        mAdapter = new NotesEditingAdapter(pictureList);
        mRvPublish.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.iv_add:
                    new RxPermissions(PublishDynamicActivity.this).request(Manifest.permission.CAMERA,
                            WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE)
                            .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    setmImg();
                                }
                            });
                    break;
                case R.id.iv_delete:
                    List<FeedbackPictureBean> pictureBeans = mAdapter.getData();
                    pictureBeans.remove(position);
                    if (!TextUtils.isEmpty(pictureBeans.get(pictureBeans.size() - 1).getAbsolutePath())) {
                        //9张图片删除时，需手动添加一张空白图片
                        pictureBeans.add(new FeedbackPictureBean());
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        });

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<FeedbackPictureBean> pictureBeans = mAdapter.getData();
            ArrayList imgList = new ArrayList();
            for (int i = 0; i < pictureBeans.size(); i++) {
                if (!TextUtils.isEmpty(pictureBeans.get(i).getAbsolutePath())) {
                    imgList.add(pictureBeans.get(i).getAbsolutePath());
                }
            }

            Intent intent = new Intent(PublishDynamicActivity.this, BigPhotoActivity.class);
            intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, imgList);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
            ArchitectUtils.startActivity(intent);
        });
    }

    /**
     * 调用相机或者相册
     */
    private void setmImg() {
        //选择图片最大数量
        List<FeedbackPictureBean> pictureBeans = mAdapter.getData();
        int mMaxSelectNum = 9 - pictureBeans.size() + 1;

        PictureSelector.create(this)
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片、视频、音频选择结果回调
        if (requestCode == PictureConfig.CHOOSE_REQUEST) {
            mSelectList = PictureSelector.obtainMultipleResult(data);
            if (!mSelectList.isEmpty()) {
                mIndex = 0;
                uploadImgOrder();

                try {
                    LoadingDialog.getInstance().show(this);
                } catch (Exception e) {
                    LogUtils.e("=====================图片上传显示dialog出错:" + e.getMessage());
                }
            }
        } else if (requestCode == 9) {
            if (data != null && !"".equals(data.getStringExtra(Constant.PUSH_TOPICID))) {
                topicId = data.getStringExtra(Constant.PUSH_TOPICID);
                topicName = data.getStringExtra(Constant.PUSH_TOPICNAME);
                mTvLabelContent.setText(topicName);
                ivTopic.setBackgroundResource(R.drawable.close_topic_ic);
            }
        }
    }

    /**
     * 按顺序进行图片上传
     */
    public void uploadImgOrder() {
        if (mIndex < mSelectList.size()) {
            LocalMedia localMedia = mSelectList.get(mIndex);
            File file = new File(localMedia.getCompressPath());
            if (!file.exists()) {
                loadNextImg();
                return;
            }
            double fileOrFilesSize = FileSizeUtil.getFileOrFilesSize(localMedia.getCompressPath(), FileSizeUtil.SIZETYPE_KB);
            LogUtils.e("========================图片大小为：" + fileOrFilesSize);
            try {
                FileUploadUtils.upload(this, file, 0, this);
            } catch (Exception e) {
                loadNextImg();
                LogUtils.e("=====================图片上传方法异常" + e.toString());
            }
        } else {
            LoadingDialog.getInstance().dismiss();
        }
    }

    private void loadNextImg() {
        mIndex += 1;
        uploadImgOrder();
    }


    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.realUrl)) {
            LogUtils.e("=====================上传返回的图片路径为空");
        } else {
            List<FeedbackPictureBean> pictureBeans = mAdapter.getData();
            int size = pictureBeans.size();
            pictureBeans.get(size - 1).setAbsolutePath(bean.realUrl);
            setAddImage(pictureBeans);
        }

        loadNextImg();
    }

    @Override
    public void onFileUploadError(Throwable e) {
        LogUtils.e("=====================图片上传失败" + e.toString());
        TipsUtil.show("图片上传失败");

        loadNextImg();
    }

    /**
     * 图片集合中新增图片处理
     */
    public void setAddImage(List<FeedbackPictureBean> pictureBeans) {
        if (pictureBeans.size() < 9) {
            pictureBeans.add(new FeedbackPictureBean());
        }
        mAdapter.notifyDataSetChanged();
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_common_right, R.id.tv_label_bg, R.id.iv_topic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_topic:
                ivTopic.setBackgroundResource(R.drawable.ic_still_more);
                mTvLabelContent.setText("选择话题");
                topicId = "";
                break;
            case R.id.tv_common_right:
                String strContent = mEtContent.getText().toString().trim();
                if (TextUtils.isEmpty(strContent)) {
                    showMessage("请输入动态内容");
                    return;
                }
                List<FeedbackPictureBean> pictureBeans = mAdapter.getData();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pictureBeans.size(); i++) {
                    if (!TextUtils.isEmpty(pictureBeans.get(i).getAbsolutePath())) {
                        sb.append(pictureBeans.get(i).getAbsolutePath()).append(",");
                    }
                }
                if (mPresenter != null) {
                    mPresenter.addTheme(strContent.trim(), topicId, StringUtil.subStrEnd(sb.toString()));
                }
                break;
            case R.id.tv_label_bg:
                //话题选择
                if (topicType != 1) {
                    Intent intent = new Intent(PublishDynamicActivity.this, AllTopicActivity.class);
                    intent.putExtra("local", "1");
                    startActivityForResult(intent, 9);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void addThemeSuccess() {
        showMessage("发布成功");
        if (topicType == 1) {
            Intent i = new Intent();
            i.putExtra(Constant.PUSH_TOPICID_CALLBACK, true);
            setResult(1001, i);
        }
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String strContent = mEtContent.getText().toString().trim();
        if (mTvContentCount != null) {
            if (TextUtils.isEmpty(strContent)) {
                mTvContentCount.setText("0/5000");
            } else {
                mTvContentCount.setText(strContent.length() + "/5000");
            }
        }

    }

    @Override
    public void showLoading() {
        if (mTvCommonRight != null) {
            mTvCommonRight.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mTvCommonRight != null) {
            mTvCommonRight.setClickable(true);
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

    private boolean mPermissions = false;

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

}
