package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ScreenLabelBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.bean.event.UserInfoUpdateEvent;
import com.phjt.shangxueyuan.di.component.DaggerBasicInfoComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.BasicInfoContract;
import com.phjt.shangxueyuan.mvp.presenter.BasicInfoPresenter;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.LimitInputTextWatcher;
import com.phjt.shangxueyuan.utils.SizeFilterWithTextAndLetter;
import com.phjt.shangxueyuan.utils.SoftKeyBoardListener;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.shangxueyuan.widgets.dialog.SexSelectionDialog;
import com.phjt.view.roundImg.RoundedImageView;
import com.phsxy.utils.KeyboardUtils;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * @author: Roy
 * date:    2020/03/27
 * company: 普华集团
 * description: 基本信息
 */
public class BasicInfoActivity extends BaseActivity<BasicInfoPresenter> implements BasicInfoContract.View,
        FileUploadUtils.UploadCallback, IWithoutImmersionBar {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_info_avatar_label)
    TextView tvInfoAvatarLabel;
    @BindView(R.id.iv_info_avatar)
    RoundedImageView ivInfoAvatar;

    @BindView(R.id.view_avatar_label)
    View viewAvatarLabel;
    @BindView(R.id.et_nick_name)
    EditText etNickName;
    @BindView(R.id.fl_nick_name)
    FrameLayout flNickName;
    @BindView(R.id.view_info_name)
    View viewInfoName;
    @BindView(R.id.tv_full_name)
    TextView tvFullName;
    @BindView(R.id.et_full_name)
    EditText etFullName;

    @BindView(R.id.fl_full_name)
    FrameLayout flFullName;
    @BindView(R.id.view_full_name)
    View viewFullName;
    @BindView(R.id.tv_info_sex_label)
    TextView tvInfoSexLabel;
    @BindView(R.id.tv_info_sex_name)
    TextView tvInfoSexName;
    @BindView(R.id.iv_arrows_sex)
    ImageView ivArrowsSex;
    @BindView(R.id.fl_sex_label)
    FrameLayout flSexLabel;
    @BindView(R.id.view_sex_label)
    View viewSexLabel;
    @BindView(R.id.tv_info_date_label)
    TextView tvInfoDateLabel;
    @BindView(R.id.tv_info_date_name)
    TextView tvInfoDateName;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;

    private int statusId = 0;
    private String mImgPath = "";
    private UserInfoBean mBeans;
    private String nickNames = "";
    private String mImgUrlPath = "";
    private String mImgUrlAbsolutePat = "";
    private boolean mPermissions = false;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBasicInfoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_basic_info;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.color_white)
                .statusBarDarkFont(true)
                .init();

        tvCommonTitle.setText("基本信息");
        tvCommonRight.setVisibility(View.VISIBLE);
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color999999));
        tvCommonRight.setText("保存");

        tvCommonRight.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvCommonRight.setTextSize(16);

        ivInfoAvatar.setEnabled(false);
        tvInfoAvatarLabel.setEnabled(false);
        etFullName.setEnabled(false);
        tvInfoSexName.setEnabled(false);
        ivArrowsSex.setEnabled(false);

        etFullName.addTextChangedListener(new LimitInputTextWatcher(etFullName, 1));
        etFullName.setFilters(new InputFilter[]{new SizeFilterWithTextAndLetter(10, 5)});

        if (mPresenter != null) {
            mPresenter.getUserInfo();
        }
        setListenerToRootView();
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_common_right, R.id.et_full_name, R.id.tv_info_sex_name,
            R.id.iv_arrows_sex, R.id.tv_info_avatar_label, R.id.iv_info_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                KeyboardUtils.hideSoftInput(this);
                finish();
                break;

            case R.id.tv_common_right:
                KeyboardUtils.hideSoftInput(this);
                setonClickComplete();
                break;
            case R.id.tv_info_sex_name:
            case R.id.iv_arrows_sex:
                initDialog();
                break;
            case R.id.iv_info_avatar:
            case R.id.tv_info_avatar_label:
                KeyboardUtils.hideSoftInput(this);
                new RxPermissions(this).request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            setmImg();
                        }
                    }
                });
                break;
            default:
                break;
        }
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
        PictureSelector.create(BasicInfoActivity.this)
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
        // 图片、视频、音频选择结果回调
        List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
        if (!selectList.isEmpty()) {
            LocalMedia localMedia = selectList.get(0);
            File file = new File(localMedia.getCompressPath());
            if (!file.exists()) {
                return;
            }
            mImgPath = file.getAbsolutePath();
            LogUtils.e("====================" + mImgPath);
            GlideUtils.load(mImgPath, ivInfoAvatar);
            FileUploadUtils.upload(this, file, 0, this);
        }
    }

    @Override
    public void onFileUploadSuccess(FileUploadUtils.UploadResultBean bean) {
        mImgUrlPath = bean.url;
        mImgUrlAbsolutePat = bean.realUrl;
    }

    @Override
    public void onFileUploadError(Throwable e) {
        showTips(e.getMessage());
    }

    /**
     * 选择性别
     */
    private void initDialog() {
        List<ScreenLabelBean> mList = new ArrayList<>();
        ScreenLabelBean bean2 = new ScreenLabelBean(1, "男", false);
        ScreenLabelBean bean1 = new ScreenLabelBean(2, "女", false);
        mList.add(bean2);
        mList.add(bean1);
        SexSelectionDialog dialog = new SexSelectionDialog(BasicInfoActivity.this, mList, (statusIds, name) -> {
            statusId = statusIds;
            if (statusId == 0) {
                statusId = mBeans.getSex();
            } else {
                tvInfoSexName.setText(name);
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * 点击完成
     */
    private void setonClickComplete() {
        String nickName = etNickName.getText().toString().trim();
        String userName = etFullName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            if (!TextUtils.isEmpty(nickNames)) {
                nickName = nickNames;
            } else {
                showTips("请输入昵称");
                return;
            }
        }

        if (mPresenter != null) {
            mPresenter.onClickUserEdit(BasicInfoActivity.this, nickName, userName, statusId, mImgUrlAbsolutePat);
        }
    }

    @Override
    public void loadDataSuccess(UserInfoBean beans) {
        if (null != beans) {
            mBeans = beans;
            ivInfoAvatar.setEnabled(true);
            tvInfoAvatarLabel.setEnabled(true);
            setonClickEdit();
            String phoneNumber = beans.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

            etFullName.setText(TextUtils.isEmpty(beans.getUserName()) ? " " : beans.getUserName());
            if (beans.getSex() == 1) {
                tvInfoSexName.setText("男");
                statusId = beans.getSex();
            } else if (beans.getSex() == 2) {
                tvInfoSexName.setText("女");
                statusId = beans.getSex();
            } else {
                tvInfoSexName.setText("请选择");
                statusId = 0;
            }
            tvInfoDateName.setText(phoneNumber);
            GlideUtils.load(!TextUtils.isEmpty(beans.getPhoto()) ? beans.getPhoto() : beans.getWxPhoto(),
                    ivInfoAvatar, R.drawable.iv_mine_avatar);
        }
    }

    /**
     * 点击编辑
     */
    private void setonClickEdit() {
        tvCommonRight.setEnabled(true);
        tvCommonRight.setTextColor(ContextCompat.getColor(this, R.color.color_4C7CFB));
        ivArrowsSex.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(mBeans.getUserName())) {
            etFullName.setText(mBeans.getUserName());
        } else {
            etFullName.setHint("请输入姓名");
        }
        if (!TextUtils.isEmpty(mBeans.getNickName())) {
            etNickName.setText(mBeans.getNickName());
            etNickName.setSelection(etNickName.length());
        } else {
            etNickName.setText("");
            etNickName.setHint("请输入昵称");
        }

        etNickName.setEnabled(true);
        etFullName.setEnabled(true);
        tvInfoSexName.setEnabled(true);
        ivArrowsSex.setEnabled(true);

        etNickName.requestFocus();
    }

    /**
     * 保存信息成功
     */
    @Override
    public void userEditSuccess() {
        EventBusManager.getInstance().post(new UserInfoUpdateEvent());
        finish();
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    /**
     * 软键盘监听
     */
    private void setListenerToRootView() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //键盘显示
                etNickName.addTextChangedListener(new LimitInputTextWatcher(etNickName, 0));
                etNickName.setFilters(new InputFilter[]{new SizeFilterWithTextAndLetter(20, 10)});
            }

            @Override
            public void keyBoardHide(int height) {
                //键盘隐藏
//                Log.e("666keyboard", "keyboard is hidden");
            }
        });

    }
}
