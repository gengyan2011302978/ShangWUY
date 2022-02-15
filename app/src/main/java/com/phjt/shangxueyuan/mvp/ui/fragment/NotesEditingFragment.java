package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.event.CommentEventBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerNotesEditingComponent;
import com.phjt.shangxueyuan.mvp.contract.NotesEditingContract;
import com.phjt.shangxueyuan.mvp.presenter.NotesEditingPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.NotesEditingAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :编辑笔记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */
public class NotesEditingFragment extends BaseNoteFragment<NotesEditingPresenter> implements NotesEditingContract.View, FileUploadUtils.UploadCallback {

    @BindView(R.id.iv_back_img)
    ImageView ivCommonBack;
    @BindView(R.id.tv_publish)
    TextView tvCommonRight;
    @BindView(R.id.iv_public)
    ImageView ivPublic;
    @BindView(R.id.rcy_photo_released)
    RecyclerView recyclePicture;
    Unbinder unbinder;

    public static final String CURRENT_TIME = "currentTime";
    public static final String BUNDLE_NOTES_EDITING = "bundle_notes";
    public static final String BUNDLE_TYPE = "bundle_type";
    private Context context;
    /**
     * 1是公开； 0私密
     */
    private int publicationStatus = 1;
    private String pointId;
    private String courseId;
    private List<FeedbackPictureBean> pictureList;
    private int addPosition;

    private boolean mPermissions = false;
    private NotesEditingAdapter feedbackAdapter;
    private boolean isCleanUp = false;
    private EditText etNotes;
    private long coursePauseTime;
    private List<String> mDatasImgList;
    int mMaxSelectNum = 9;
    /**
     * mType :0 添加笔记，1：修改笔记
     */
    private int mType;

    /**
     * 是否是课程评论 默认为false(笔记)
     */
    private boolean isComment;
    public static final String BUNDLE_IS_COMMENT = "bundle_is_comment";
    private String courseType;
    /**
     * 训练营 任务id
     */
    private String trainingSecondId;

    public static NotesEditingFragment newInstance() {
        return new NotesEditingFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNotesEditingComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public Dialog onCreateDialog(@androidx.annotation.Nullable Bundle savedInstanceState) {
        //返回BottomSheetDialog的实例
        return new BottomSheetDialog(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.activity_editing_notes, container, false);
        unbinder = ButterKnife.bind(this, view);
        setDatas();
        return view;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        //获取dialog对象
        BottomSheetDialog mBottomSheetDialog = (BottomSheetDialog) getDialog();
        //把windowsd的默认背景颜色去掉，不然圆角显示不见
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //获取diglog的根部局
        FrameLayout bottomSheet = mBottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //获取根部局的LayoutParams对象
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
            bottomSheet.setLayoutParams(layoutParams);

            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight即弹窗的最大高度
            behavior.setPeekHeight(getPeekHeight());
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        mBottomSheetDialog.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != etNotes && !isCleanUp && mType == 0) {
            etNotes.setText("");
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                //拦截到的返回事件
                return true;
            }
            return false;
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        publicationStatus = 1;
        mMaxSelectNum = 9;
        FragmentActivity activity = getActivity();
        if (activity instanceof CourseDetailActivity) {
            CourseDetailActivity activitys = (CourseDetailActivity) activity;
            activitys.resumePlay();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FrameLayout contentParent = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        if (null != contentParent) {
            contentParent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SoftKeyboardUtil.setHideKeyboard(contentParent);
                    isCleanUp = false;
                }
            }, 100);
        }
    }

    private void setDatas() {
        Bundle bundle = getArguments();
        tvCommonRight.setEnabled(true);
        pictureList = new ArrayList<>();
        if (bundle != null) {
            pointId = bundle.getString(Constant.BUNDLE_POINT_ID);
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            courseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            coursePauseTime = bundle.getLong(CURRENT_TIME, 0);
            isComment = bundle.getBoolean(BUNDLE_IS_COMMENT, false);
            trainingSecondId = bundle.getString(Constant.BUNDLE_TRAINING_SECOND_ID);
            mType = bundle.getInt(BUNDLE_TYPE, 0);
        }
        initAdapter();
        setEditText();
        if (mType == 1) {
            MyNotesBean myNotesBea = (MyNotesBean) bundle.getSerializable(BUNDLE_NOTES_EDITING);
            setTextData(myNotesBea);
        }

        ivPublic.setVisibility(isComment ? View.GONE : View.VISIBLE);

    }

    private void setTextData(MyNotesBean myNotesBea) {
        if (null != etNotes && feedbackAdapter != null && ivPublic != null && myNotesBea != null) {
            if (!TextUtils.isEmpty(myNotesBea.getNoteContent())) {
                etNotes.setText(String.valueOf(myNotesBea.getNoteContent()));
                etNotes.setSelection(etNotes.getText().length());
            }

            pictureList.clear();
            if (!TextUtils.isEmpty(myNotesBea.getNotesImg())) {
                List<String> imgList = Arrays.asList(myNotesBea.getNotesImg().split(","));
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

            if (myNotesBea.getOpenState() == 1) {
                publicationStatus = 1;
                ivPublic.setSelected(false);
            } else {
                publicationStatus = 0;
                ivPublic.setSelected(true);
            }
        }
    }

    private void initAdapter() {
        mDatasImgList = new ArrayList<>();
        if (mType == 0 && pictureList.size() == 0) {
            pictureList.add(new FeedbackPictureBean());
        }
        recyclePicture.setNestedScrollingEnabled(false);
        recyclePicture.setVerticalScrollBarEnabled(true);
        recyclePicture.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclePicture.setLayoutManager(layoutManager);
        View headView = LayoutInflater.from(context).inflate(R.layout.fragment_notes_editing_header, null);
        etNotes = headView.findViewById(R.id.et_notes);
        etNotes.setFocusable(true);
        etNotes.setFocusableInTouchMode(true);
        etNotes.requestFocus();
        etNotes.setHint(isComment ? "请输入您的评论" : "请输入笔记内容");

        feedbackAdapter = new NotesEditingAdapter(pictureList);
        feedbackAdapter.bindToRecyclerView(recyclePicture);

        feedbackAdapter.setHeaderView(headView);
        feedbackAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mDatasImgList.size() > 0) {
                Intent intent = new Intent(mContext, BigPhotoActivity.class);
                intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) mDatasImgList);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                ArchitectUtils.startActivity(intent);
            }
        });

        setOnItemChildClick();
    }

    /**
     * 图片点击
     */
    private void setOnItemChildClick() {
        feedbackAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_add) {
                    //添加图片
                    uploadImg();
                    isCleanUp = true;
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
                    if (1 == mType && addPosition == Constant.MAX_PICTURE_NUMBER) {
                        FeedbackPictureBean picture = new FeedbackPictureBean();
                        picture.setUrlPath("");
                        picture.setAbsolutePath("");
                        pictureList.add(picture);
                        feedbackAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
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
                        FileUploadUtils.upload(getActivity(), file, 0, this);
                    } catch (Exception e) {
                        LogUtils.e("=====================图片上传异常" + e.toString());
                    }
                }
            }
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


    /**
     * 调用相机或者相册
     */
    private void setmImg() {
        PictureSelector.create(getActivity())
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

    @SingleClick
    @OnClick({R.id.iv_back_img, R.id.tv_publish, R.id.iv_public})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_img:
                SoftKeyboardUtil.setHideKeyboard(ivCommonBack);
                if (null != etNotes) {
                    etNotes.setText("");
                }
                dismiss();
                isCleanUp = false;
                break;
            case R.id.tv_publish:
                SoftKeyboardUtil.setHideKeyboard(tvCommonRight);
                String mContent = "";
                if (null != etNotes) {
                    mContent = etNotes.getText().toString().trim();
                }
                if (TextUtils.isEmpty(mContent)) {
                    TipsUtil.show(isComment ? "请输入评论内容" : "请输入笔记内容");
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
                    tvCommonRight.setEnabled(false);
                    if (isComment) {
                        if (TextUtils.isEmpty(trainingSecondId)) {
                            // 发布课程评论
                            mPresenter.addComment(courseId, mContent, img.toString(), courseType);
                        } else {
                            //发布训练营评论
                            mPresenter.addTrainingComment(courseId, trainingSecondId, mContent, img.toString());
                        }
                    } else {
                        //发布笔记
                        mPresenter.addNotess(courseId, pointId, mContent, publicationStatus, img.toString(), coursePauseTime, mType, courseType);
                        UmengUtil.umengCount(getActivity(), ConstantUmeng.COURSE_CLICK_COLLECTION_WRITE_NOTES);
                    }
                }

                break;
            case R.id.iv_public:
                if (ivPublic.isSelected()) {
                    publicationStatus = 1;
                    ivPublic.setSelected(false);
                } else {
                    publicationStatus = 0;
                    ivPublic.setSelected(true);
                }
                break;
            default:
                break;
        }
    }


    private void setEditText() {
        if (null != etNotes) {
            etNotes.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String tvNickName = etNotes.getText().toString().trim();
                    if (tvNickName.length() == 0) {
                        tvCommonRight.setBackgroundResource(R.drawable.bg_publish_un);
                        tvCommonRight.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
                    } else {
                        tvCommonRight.setBackgroundResource(R.drawable.bg_publish);
                        tvCommonRight.setTextColor(Color.WHITE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
    }

    @Override
    public void addNotessSuccess() {
        EventBusManager.getInstance().post(new EventBean(EventBean.NOTES_FOUND, null));
        if (publicationStatus == 1) {
            TipsUtil.show(" 提交成功，等待审核");
        } else {
            TipsUtil.show("发表成功");
        }
        dismiss();
        isCleanUp = false;
    }

    @Override
    public void addNotessFail() {
        tvCommonRight.setEnabled(true);
    }

    @Override
    public void addCommentSuccess() {
        EventBusManager.getInstance().post(new CommentEventBean());
        TipsUtil.show(" 提交成功，等待审核");
        dismiss();
        isCleanUp = false;
    }

    @Override
    public void addTrainingCommentSuccess() {
        EventBusManager.getInstance().post(new CommentEventBean());
        TipsUtil.show(" 提交成功");
        dismiss();
        isCleanUp = false;
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
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[i])) {
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
        DialogUtils.showCancelSureDialog(getActivity(), "无法使用相册",
                "可以到手机系统“设置”中开启？", getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_sure),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        //引导用户至设置页手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getApplicationContext().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        isCleanUp = true;
                    }
                });
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
            feedbackAdapter.notifyItemChanged(addPosition);
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

    /**
     * 弹窗高度，默认为屏幕高度的四分之三
     * 子类可重写该方法返回peekHeight
     *
     * @return height
     */
    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return (int) (peekHeight - peekHeight / 9.5);
    }

    @Override
    public void setData(@Nullable Object data) {
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
