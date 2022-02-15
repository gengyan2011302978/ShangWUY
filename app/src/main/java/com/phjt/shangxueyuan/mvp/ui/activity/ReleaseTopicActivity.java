package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.phjt.shangxueyuan.bean.ReleaseCoverBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerReleaseTopicComponent;
import com.phjt.shangxueyuan.mvp.contract.ReleaseTopicContract;
import com.phjt.shangxueyuan.mvp.presenter.ReleaseTopicPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ReleaseCoverAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:28
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ReleaseTopicActivity extends BaseActivity<ReleaseTopicPresenter> implements ReleaseTopicContract.View,
        FileUploadUtils.UploadCallback {

    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.share_titile)
    ConstraintLayout shareTitile;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_title_numb)
    TextView tvTitleNumb;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.tv_desc_numb)
    TextView tvDescNumb;
    @BindView(R.id.iv_cover_zero)
    ImageView ivCoverZero;
    @BindView(R.id.iv_chenck_zero)
    ImageView ivChenckZero;
    @BindView(R.id.relat_topic)
    RelativeLayout relatTopic;
    @BindView(R.id.recycle_cover)
    RecyclerView recycleCover;
    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.relat_photo)
    RelativeLayout relatPhoto;
    @BindView(R.id.tv_take_photo)
    TextView tvTakePhoto;

    private ReleaseCoverAdapter releaseCoverAdapter;
    private List<ReleaseCoverBean> list = new ArrayList<>();
    private String coverImg = "";
    //是否可以选择相册
    private boolean isTakePhoto = true;
    /**
     * 话题id
     */
    private String topicId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerReleaseTopicComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_release_topic;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRv();
        addTextWatcherListener();

        Intent intent = getIntent();
        topicId = intent.getStringExtra(Constant.BUNDLE_TOPIC_ID);
        String title = intent.getStringExtra(Constant.BUNDLE_TOPIC_TITLE);
        String content = intent.getStringExtra(Constant.BUNDLE_TOPIC_CONTENT);
        coverImg = intent.getStringExtra(Constant.BUNDLE_TOPIC_BG);

        tvTakePhoto.setTextColor(Color.rgb(51, 51, 51));
        tvCommonRight.setText("发布");
        tvCommonRight.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(topicId)) {
            //内容不为空时，是编辑话题
            etTitle.setText(title);
            etDesc.setText(content);
            AppImageLoader.loadResUrl(coverImg, ivCoverZero);
            isShowSinglePhoto(true);

            tvCommonTitle.setText("编辑话题");
            tvCommonRight.setTextColor(Color.rgb(16, 116, 255));
            tvCommonRight.setClickable(true);
        } else {
            tvCommonTitle.setText("发布话题");
            tvCommonRight.setTextColor(Color.rgb(153, 153, 153));
            tvCommonRight.setClickable(false);
        }

        if (mPresenter != null) {
            mPresenter.imgConfig();
        }
    }

    private void initRv() {
        releaseCoverAdapter = new ReleaseCoverAdapter(list);
        recycleCover.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recycleCover.setAdapter(releaseCoverAdapter);

        releaseCoverAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReleaseCoverBean> releaseCoverBeans = adapter.getData();
                ReleaseCoverBean releaseCoverBean = (ReleaseCoverBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_cover_one:
                        if (releaseCoverBean.getCheckStatus() == 1) {
                            releaseCoverBean.setCheckStatus(2);
                            tvTakePhoto.setTextColor(Color.rgb(51, 51, 51));
                            isTakePhoto = true;
                            coverImg = "";
                        } else {
                            releaseCoverBean.setCheckStatus(1);
                            tvTakePhoto.setTextColor(Color.rgb(153, 153, 153));
                            isTakePhoto = false;
                            coverImg = releaseCoverBean.getImgUrl();
                        }
                        for (int i = 0; i < releaseCoverBeans.size(); i++) {
                            if (i != position) {
                                releaseCoverBeans.get(i).setCheckStatus(2);
                            }
                        }
                        releaseCoverAdapter.notifyDataSetChanged();
                        selectRelease();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void addTextWatcherListener() {
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTitleNumb.setText(etTitle.getText().length() + "/" + "13");
                selectRelease();
            }
        });
        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvDescNumb.setText(etDesc.getText().length() + "/" + "50");
                selectRelease();
            }
        });
    }

    private void selectRelease() {
        if (tvCommonRight != null) {
            if (!"".equals(coverImg) && !"".equals(etDesc.getText().toString()) && !"".equals(etTitle.getText().toString())) {
                tvCommonRight.setTextColor(Color.rgb(16, 116, 255));
                tvCommonRight.setClickable(true);
            } else {
                tvCommonRight.setTextColor(Color.rgb(153, 153, 153));
                tvCommonRight.setClickable(false);
            }
        }
    }

    @SuppressLint("CheckResult")
    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_common_right, R.id.iv_chenck_zero, R.id.relat_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.tv_common_right:
                String title = etTitle.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                if (!TextUtils.isEmpty(coverImg) && !TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc)) {
                    if (mPresenter != null) {
                        if (TextUtils.isEmpty(topicId)) {
                            //新增话题
                            mPresenter.addTopic(2, title, desc, coverImg);
                        } else {
                            //编辑话题
                            mPresenter.editTopic(topicId, title, desc, coverImg);
                        }
                    }
                }
                break;
            case R.id.relat_photo:
                if (isTakePhoto) {
                    new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE)
                            .subscribe(aBoolean -> {
                                if (aBoolean) {
                                    setmImg();
                                }
                            });
                }
                break;
            case R.id.iv_chenck_zero:
                coverImg = "";
                isShowSinglePhoto(false);
                break;
            default:
                break;
        }
    }

    /**
     * 调用相机或者相册
     */
    private void setmImg() {
        PictureSelector.create(ReleaseTopicActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .isCamera(true)
                .selectionMode(PictureConfig.SINGLE)
                .isEnableCrop(true)
                .withAspectRatio(1, 1)
                .showCropFrame(true)
                .showCropGrid(false)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        // 图片、视频、音频选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        if (!selectList.isEmpty()) {
            LocalMedia localMedia = selectList.get(0);
            File file = new File(localMedia.getCompressPath());
            if (!file.exists()) {
                return;
            }
            String mImgPath = file.getAbsolutePath();
            LogUtils.e("====================" + mImgPath);
            GlideUtils.load(mImgPath, ivCoverZero);

            try {
                FileUploadUtils.upload(this, file, 0, this);
            } catch (Exception e) {
                LogUtils.e("=====================图片上传方法异常" + e.toString());
            }
        }
    }

    @Override
    public void imgConfigSuccess(List<ReleaseCoverBean> list) {
        if (releaseCoverAdapter != null) {
            releaseCoverAdapter.setNewData(list);
        }
    }

    @Override
    public void addTopicSuccess(String msg) {
        Intent intent = new Intent(ReleaseTopicActivity.this, TopicInfoActivity.class);
        intent.putExtra("topicId", msg);
        startActivity(intent);
        ToastUtils.show("话题创建成功");
        finish();
    }

    @Override
    public void addTopicFaile(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void editTopicSuccess() {
        ToastUtils.show("话题编辑成功");
        setResult(1002);
        finish();
    }

    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        coverImg = bean.realUrl;
        isShowSinglePhoto(true);
    }

    private void isShowSinglePhoto(boolean isShow) {
        if (relatTopic != null) {
            relatTopic.setVisibility(isShow ? View.VISIBLE : View.GONE);
        }
        if (tvText != null) {
            tvText.setVisibility(isShow ? View.GONE : View.VISIBLE);
        }
        if (recycleCover != null) {
            recycleCover.setVisibility(isShow ? View.GONE : View.VISIBLE);
        }
        if (relatPhoto != null) {
            relatPhoto.setVisibility(isShow ? View.GONE : View.VISIBLE);
        }
        selectRelease();
    }

    @Override
    public void onFileUploadError(Throwable e) {
        showTips(e.getMessage());
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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
