package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerPutQuestionsToComponent;
import com.phjt.shangxueyuan.interf.IEditTextAutomaticHide;
import com.phjt.shangxueyuan.mvp.contract.PutQuestionsToContract;
import com.phjt.shangxueyuan.mvp.presenter.PutQuestionsToPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.NotesEditingAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.LoadingViewUtil;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.SlideButton;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 14:32
 * @description :发表问题
 */
public class PutQuestionsToActivity extends BaseActivity<PutQuestionsToPresenter> implements PutQuestionsToContract.View, FileUploadUtils.UploadCallback, IEditTextAutomaticHide {
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_title_right)
    TextView tvCommonRight;
    @BindView(R.id.et_put_questions_to_title)
    EditText etPutQuestionsToTitle;
    @BindView(R.id.et_put_questions_to_content)
    EditText etPutQuestionsToContent;
    @BindView(R.id.rcy_photo_released)
    RecyclerView rcyReleased;
    @BindView(R.id.view_put_questions_to_line)
    View viewPutQuestionsToLine;
    @BindView(R.id.tv_privacy_choice)
    TextView tvPrivacyChoice;
    @BindView(R.id.tv_privacy_settings)
    TextView tvPrivacySettings;
    @BindView(R.id.sb_privacy_settings)
    SlideButton mSlideButton;

    private int isOpen = 0;
    private int pitchColor;
    private int pitchColorun;
    private String realmId = "";
    private List<FeedbackPictureBean> pictureList;
    private List<String> mDatasImgList;
    int mMaxSelectNum = 9;
    private NotesEditingAdapter feedbackAdapter;
    private int addPosition;
    private String mTutorId = "";
    private String mQuestionCoin = "";
    private int mCommodityType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPutQuestionsToComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_put_questions_to;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .keyboardEnable(true)
                .init();
        tvCommonTitle.setText(getString(R.string.questions_ask_questions));
        tvCommonRight.setVisibility(View.VISIBLE);
        tvCommonRight.setText(getString(R.string.questions_answers_issue_issue));
        mTutorId = getIntent().getStringExtra(Constant.TUTOR_ID);
        mCommodityType = getIntent().getIntExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 0);
        mQuestionCoin = getIntent().getStringExtra(Constant.BUNDLE_ORDER_REALPRICE);
        pitchColor = ContextCompat.getColor(this, R.color.color_333333);
        pitchColorun = ContextCompat.getColor(this, R.color.color_333333);
        setPhotoAdapter();
        setSlideButton();
        initExtChangedListener();
        //点击外部，关闭键盘
        if (PutQuestionsToActivity.this instanceof IEditTextAutomaticHide) {
            FrameLayout contentParent =
                    this.getWindow().getDecorView().findViewById(android.R.id.content);
            View content = contentParent.getChildAt(0);
            if (content != null) {
                SoftKeyboardUtil.recursiveFindEditAndSetOnTouchListener(content);
            }
        }
    }

    @Override
    public void showLoading() {
        LoadingViewUtil.getInstance().show();
    }

    @Override
    public void hideLoading() {
        LoadingViewUtil.getInstance().hide();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setSlideButton() {
        mSlideButton.setSmallCircleModel(ContextCompat.getColor(this, R.color.color_FFFF650C),
                ContextCompat.getColor(this, R.color.color_FFFF650C),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.color_CCCCCC));

        mSlideButton.setChecked(true);
        mSlideButton.setOnCheckedListener(isChecked -> {
            if (isChecked) {
                isOpen = 0;
            } else {
                isOpen = 1;
            }
            tvPrivacySettings.setText(isOpen == 0 ? "公开" : "不公开");
            tvPrivacySettings.setTextColor(isOpen == 0 ? pitchColor : pitchColorun);
        });

    }

    private void setPhotoAdapter() {
        pictureList = new ArrayList<>();
        mDatasImgList = new ArrayList<>();
        if (pictureList.size() == 0) {
            pictureList.add(new FeedbackPictureBean());
        }
        rcyReleased.setNestedScrollingEnabled(false);
        rcyReleased.setVerticalScrollBarEnabled(true);
        rcyReleased.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rcyReleased.setLayoutManager(layoutManager);

        feedbackAdapter = new NotesEditingAdapter(pictureList);
        feedbackAdapter.bindToRecyclerView(rcyReleased);
        feedbackAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mDatasImgList.size() > 0) {
                Intent intent = new Intent(this, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) mDatasImgList);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            }
        });

        setOnItemChildClick();
    }

    @OnClick({R.id.iv_common_back, R.id.tv_title_right, R.id.tv_privacy_choice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;
            case R.id.tv_title_right:
                KeyboardUtils.hideSoftInput(this);
                String mTitles = etPutQuestionsToTitle.getText().toString().trim();
                String mContents = etPutQuestionsToContent.getText().toString().trim();
                KeyboardUtils.hideSoftInput(this);
                if (TextUtils.isEmpty(mTitles)) {
                    TipsUtil.show("请输入提问标题");
                    return;
                }
                if (TextUtils.isEmpty(realmId)) {
                    TipsUtil.show("请选择专业领域");
                    return;
                }

                Intent intent = new Intent(this, OrderConfirmActivity.class);
                intent.putExtra(Constant.BUNDLE_ORDER_NAME, getString(R.string.string_questions_answers));
                intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, mQuestionCoin);
                intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, mCommodityType);
                intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, mTutorId);
                startActivity(intent);
                break;
            case R.id.tv_privacy_choice:
                //去选择
                if (mPresenter != null) {
                    mPresenter.getRealmSelectList(mTutorId);
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void loadDataSuccess(List<ScreenlBean> beans) {
        DialogUtils.showViewProblemsDialog(this, beans, new DialogUtils.OnClickDialogListener() {
            @Override
            public void sendProblemsComment(String comment, String id) {
                tvPrivacyChoice.setText(TextUtils.isEmpty(comment) ? "请选择" : comment);
                realmId = id;
                tvPrivacyChoice.setTextColor(ContextCompat.getColor(PutQuestionsToActivity.this, R.color.color_FF000000));
            }
        });
    }

    @Override
    public void sendQuestionSuccess() {
        tvCommonRight.setBackgroundResource(R.drawable.bg_issue_un);
        tvCommonRight.setEnabled(false);
        TipsUtil.show("发布成功");
        EventBusManager.getInstance().post(new EventBean(EventBean.MY_ANSWERS_ID, ""));
        this.findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);

    }

    @Override
    public void sendQuestionFail(String msg) {

    }


    /**
     * 获取提交图片
     *
     * @return
     */
    private String getQuestionImg() {
        String questionImg = "";
        if (pictureList != null && pictureList.size() > 0) {
            StringBuilder img = new StringBuilder();
            for (FeedbackPictureBean bean : pictureList) {
                if (!TextUtils.isEmpty(bean.getAbsolutePath())) {
                    img.append(bean.getAbsolutePath()).append(",");
                }
            }
            if (img.length() > 0) {
                img.replace(img.length() - 1, img.length(), "");
                questionImg = img.toString();
            }
        }

        return questionImg;
    }

    /**
     * 图片点击
     */
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


    /**
     * 调用相机或者相册
     */
    private void setmImg() {
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

    }

    private void initExtChangedListener() {
        etPutQuestionsToTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = etPutQuestionsToTitle.getText();
                if (50 == editable.length()) {
                    TipsUtil.show("标题最多输入50个汉字");
                }
                if (editable.length() > 0) {
                    tvCommonRight.setBackgroundResource(R.drawable.bg_issue);
                } else {
                    tvCommonRight.setBackgroundResource(R.drawable.bg_issue_un);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        etPutQuestionsToContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = etPutQuestionsToContent.getText();
                if (500 == editable.length()) {
                    TipsUtil.show("最多输入500个汉字");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            int payType = eventBean.getPayType();
            int quantity = eventBean.getQuantity();
            if (type == EventBean.ANSWERS_PAY && mPresenter != null) {
                String questionImg = getQuestionImg();
                String mTitles = etPutQuestionsToTitle.getText().toString().trim();
                String mContents = etPutQuestionsToContent.getText().toString().trim();
                mPresenter.sendQuestion(mTutorId, mTitles, mContents, realmId, isOpen, questionImg, payType, quantity);
            }
        }
    }
}