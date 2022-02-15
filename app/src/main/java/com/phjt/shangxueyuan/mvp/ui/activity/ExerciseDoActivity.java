package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseItemBean;
import com.phjt.shangxueyuan.bean.ExerciseItemChoseBean;
import com.phjt.shangxueyuan.bean.ExerciseSubmitBean;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.di.component.DaggerExerciseDoComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.ExerciseDoContract;
import com.phjt.shangxueyuan.mvp.presenter.ExerciseDoPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExerciseDoAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.NotesEditingAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.FileSizeUtil;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideEngine;
import com.phjt.shangxueyuan.utils.LoadingDialog;
import com.phjt.shangxueyuan.utils.StringUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zzhoujay.richtext.RichText;

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

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 写作业
 */
public class ExerciseDoActivity extends BaseActivity<ExerciseDoPresenter> implements
        ExerciseDoContract.View, FileUploadUtils.UploadCallback, IWithoutImmersionBar {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.rv_do_exercise)
    RecyclerView mRvDoExercise;
    @BindView(R.id.tv_submit)
    RoundTextView mTvSubmit;

    /**
     * 页面Title
     */
    private TextView mTvTitle;
    private TextView mTvExerciseState;
    private ConstraintLayout mClSubmitAnswer;
    private TextView mRelevantCourse;
    private TextView mTvRelevantCourse;
    /**
     * 选择的图片集合
     */
    private List<LocalMedia> mSelectList;
    /**
     * 当前上传的顺序
     */
    private int mIndex = 0;
    private NotesEditingAdapter mImgAdapter;
    private int mImgPosition;

    /**
     * 写作业页面的adapter
     */
    private ExerciseDoAdapter mDoAdapter;

    /**
     * 课程或专栏ID
     */
    private String couId;
    /**
     * 作业id
     */
    private String exerciseId;
    /**
     * 作业type
     */
    private Integer exerciseType;
    /**
     * 是否是修改作业，默认为false
     */
    private boolean isUpdateExercise;
    /**
     * 训练营id 训练营任务小节id
     */
    private String mTrainingCampId;
    private String mNoteTaskId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerExerciseDoComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_exercise_do;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true).keyboardEnable(true)
                .setOnKeyboardListener((isPopup, keyboardHeight) -> {
                    if (mTvSubmit != null) {
                        mTvSubmit.setVisibility(isPopup ? View.GONE : View.VISIBLE);
                    }
                    //处理单个带长图的简答题 软键盘弹出页面滚动的问题
                    if (isPopup && mRvDoExercise != null && mDoAdapter != null && mDoAdapter.getData().size() == 1) {
                        ExerciseItemBean exerciseItemBean = mDoAdapter.getData().get(0);
                        int questionType = exerciseItemBean.getQuestionType();
                        if (questionType == 5 || questionType == 6) {
                            mRvDoExercise.smoothScrollToPosition(mDoAdapter.getItemCount());
                        }
                    }
                })
                .init();

        RichText.initCacheDir(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvDoExercise.setLayoutManager(layoutManager);
        mDoAdapter = new ExerciseDoAdapter(this, null);
        mRvDoExercise.setAdapter(mDoAdapter);

        View header = LayoutInflater.from(this).inflate(R.layout.item_exercise_do_header, null);
        mTvTitle = header.findViewById(R.id.tv_title);
        mTvExerciseState = header.findViewById(R.id.tv_exercise_state);
        mClSubmitAnswer = header.findViewById(R.id.cl_submit_answer_success);
        mRelevantCourse = header.findViewById(R.id.relevant_course);
        mTvRelevantCourse = header.findViewById(R.id.tv_relevant_course);
        mDoAdapter.setHeaderView(header);
        mTvRelevantCourse.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(couId)) {
                Intent intent = new Intent(this, CourseDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, couId);
                intent.putExtra(Constant.BUNDLE_ANSWER_TO_COMMENT, true);
                startActivity(intent);
            }
        });

        mTvTitle.setVisibility(View.VISIBLE);
        mClSubmitAnswer.setVisibility(View.GONE);

        //添加图片
        mDoAdapter.setChoseAnswerImg(new ExerciseDoAdapter.IChoseAnswerImg() {
            @SuppressLint("CheckResult")
            @Override
            public void choseImg(int position, NotesEditingAdapter imgAdapter) {
                mImgAdapter = imgAdapter;
                mImgPosition = position;
                new RxPermissions(ExerciseDoActivity.this).request(Manifest.permission.CAMERA,
                        WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                setmImg();
                            }
                        });
            }
        });

        Intent intent = getIntent();
        couId = intent.getStringExtra(Constant.PUNCH_CARDS_COURSE_ID);
        exerciseId = intent.getStringExtra(Constant.BUNDLE_EXERCISE_ID);
        String exerciseBookId = intent.getStringExtra(Constant.BUNDLE_EXERCISE_BOOK_ID);
        mTrainingCampId = intent.getStringExtra(Constant.BUNDLE_TRAINING_CAMP_ID);
        mNoteTaskId = intent.getStringExtra(Constant.BUNDLE_NODE_TASK_ID);
        isUpdateExercise = intent.getBooleanExtra(Constant.BUNDLE_EXERCISE_UPDATE, false);

        if (mPresenter != null) {
            mPresenter.getExerciseBookDetail(exerciseId, couId, exerciseBookId, mTrainingCampId);
        }
    }

    @Override
    public void showExerciseList(ExerciseBean exerciseBean, boolean isSubmitSuccess) {
        exerciseId = exerciseBean.getId();
        exerciseType = exerciseBean.getExerciseType();
        couId = exerciseBean.getCouId();

        //作业状态值 0未提交 1已提交 2已点评
        int state = exerciseBean.getState();
        if (mTvTitle != null && mTvExerciseState != null && mTvRelevantCourse != null && mTvSubmit != null) {
            mTvTitle.setText(exerciseBean.getExerciseName());
            mTvRelevantCourse.setText(exerciseBean.getCouName());
            mTvSubmit.setText(getResources().getString(state == 0 ? R.string.exercise_submit :
                    TextUtils.isEmpty(mTrainingCampId) ? R.string.exercise_back_to_list : R.string.exercise_back));
            mTvCommonTitle.setText(state == 0 ? "写作业" : "我的答案");
            mTvTitle.setVisibility(state == 0 ? View.VISIBLE : View.GONE);
            mTvExerciseState.setVisibility(state == 0 ? View.VISIBLE : View.GONE);

            if (isUpdateExercise) {
                mTvCommonTitle.setText(getResources().getString(R.string.exercise_update));
                mTvSubmit.setText(getResources().getString(R.string.exercise_update));
                mTvTitle.setVisibility(View.VISIBLE);
            }
        }

        if (isSubmitSuccess) {
            mTvCommonTitle.setText("提交作业成功");
            if (TextUtils.isEmpty(mTrainingCampId)) {
                mTvSubmit.setText(getResources().getString(R.string.exercise_back_to_list));
            } else {
                //训练营跳转
                mTvSubmit.setVisibility(View.GONE);
                mRelevantCourse.setVisibility(View.GONE);
                mTvRelevantCourse.setVisibility(View.GONE);
            }

            if (mTvTitle != null && mClSubmitAnswer != null) {
                mTvTitle.setVisibility(View.GONE);
                mTvExerciseState.setVisibility(View.GONE);
                mClSubmitAnswer.setVisibility(View.VISIBLE);
            }
            if (mRvDoExercise != null) {
                mRvDoExercise.scrollToPosition(0);
            }
            checkHasAnalysis();
        } else {
            if (isUpdateExercise) {
                state = 4;
            }
        }

        List<ExerciseItemBean> itemBeans = exerciseBean.getQuestionVoList();
        if (mDoAdapter != null) {
            mDoAdapter.setState(state);
            mDoAdapter.setNewData(itemBeans);
        }
    }

    /**
     * 判断是否有简答题，有这弹框提示
     */
    private void checkHasAnalysis() {
        if (mDoAdapter != null) {
            List<ExerciseItemBean> itemBeanList = mDoAdapter.getData();
            for (int i = 0; i < itemBeanList.size(); i++) {
                ExerciseItemBean exerciseItemBean = itemBeanList.get(i);
                //试题类型：1.单选；2.多选；3.判断；4.不定项；5.简答题；6.论述题
                int questionType = exerciseItemBean.getQuestionType();
                if (questionType == 5 || questionType == 6) {
                    DialogUtils.showExerciseSubmitSuccessDialog(this, null);
                    break;
                }
            }
        }
    }


    /**
     * 调用相机或者相册
     */
    private void setmImg() {
        //选择图片最大数量
        List<FeedbackPictureBean> pictureBeans = mImgAdapter.getData();
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
                try {
                    LoadingDialog.getInstance().show(this);
                } catch (Exception e) {
                    LogUtils.e("=====================图片上传显示dialog出错:" + e.getMessage());
                }

                mIndex = 0;
                uploadImgOrder();
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
            List<FeedbackPictureBean> pictureBeans = mImgAdapter.getData();
            int size = pictureBeans.size();
            pictureBeans.get(size - 1).setAbsolutePath(bean.realUrl);

            if (pictureBeans.size() < 9) {
                pictureBeans.add(new FeedbackPictureBean());
            }

            List<FeedbackPictureBean> pictureBeansss = mImgAdapter.getData();
            mDoAdapter.getData().get(mImgPosition - mDoAdapter.getHeaderLayoutCount()).setUserChoseImg(pictureBeansss);

            mImgAdapter.notifyDataSetChanged();
        }

        loadNextImg();
    }

    @Override
    public void onFileUploadError(Throwable e) {
        LogUtils.e("=====================图片上传失败" + e.toString());
        TipsUtil.show("图片上传失败");

        loadNextImg();
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        String submitContent = mTvSubmit.getText().toString();
        switch (view.getId()) {
            case R.id.iv_common_back:
                onBackClick();
                break;
            case R.id.tv_submit:
                if (TextUtils.equals(getResources().getString(R.string.exercise_submit), submitContent)) {
                    submitAnswer();
                } else if (TextUtils.equals(getResources().getString(R.string.exercise_update), submitContent)) {
                    submitUpdateAnswer();
                } else {
                    onBackClick();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 返回事件
     */
    private void onBackClick() {
        String submitContent = mTvSubmit.getText().toString();
        if ((TextUtils.isEmpty(mTrainingCampId) && TextUtils.equals(getResources().getString(R.string.exercise_back_to_list), submitContent))
                || (!TextUtils.isEmpty(mTrainingCampId) && mClSubmitAnswer.getVisibility() == View.VISIBLE)) {
            setResult(200);
            finish();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 提交作业
     */
    private void submitAnswer() {
        //提交答案集合
        List<ExerciseSubmitBean> submitBeanList = new ArrayList<>();
        //是否一题没答 (只要有答案就置为false)
        boolean isAllUnanswered = true;
        //是否有没答的题 (只要有未答的置为false)
        boolean isHasUnanswered = true;

        List<ExerciseItemBean> itemBeanList = mDoAdapter.getData();
        for (int i = 0; i < itemBeanList.size(); i++) {
            ExerciseItemBean exerciseItemBean = itemBeanList.get(i);
            StringBuilder sb = new StringBuilder();
            //试题类型：1.单选；2.多选；3.判断；4.不定项；5.简答题；6.论述题
            int questionType = exerciseItemBean.getQuestionType();
            if (questionType == 1 || questionType == 2 || questionType == 3 || questionType == 4) {
                List<ExerciseItemChoseBean> itemChoseBeanList = exerciseItemBean.getOptionVos();
                for (int j = 0; j < itemChoseBeanList.size(); j++) {
                    ExerciseItemChoseBean itemChoseBean = itemChoseBeanList.get(j);
                    if (itemChoseBean != null && itemChoseBean.isChose()) {
                        sb.append(itemChoseBean.getOptionId()).append(",");
                    }
                }
                exerciseItemBean.setUserAnswer(StringUtil.subStrEnd(sb.toString()));
            } else if (questionType == 5 || questionType == 6) {
                List<FeedbackPictureBean> pictureBeans = exerciseItemBean.getUserChoseImg();
                if (pictureBeans != null) {
                    for (int j = 0; j < pictureBeans.size(); j++) {
                        if (!TextUtils.isEmpty(pictureBeans.get(j).getAbsolutePath())) {
                            sb.append(pictureBeans.get(j).getAbsolutePath()).append(",");
                        }
                    }
                }
                exerciseItemBean.setUserAnswerImg(StringUtil.subStrEnd(sb.toString()));
            }

            if (!TextUtils.isEmpty(exerciseItemBean.getUserAnswer())) {
                //有答案
                if (isAllUnanswered) {
                    isAllUnanswered = false;
                }
            } else {
                //未选答案
                if (isHasUnanswered) {
                    isHasUnanswered = false;
                }
            }

            ExerciseSubmitBean submitBean = new ExerciseSubmitBean();
            submitBean.setUserAnswerRecordId(exerciseItemBean.getUserAnswerRecordId());
            submitBean.setExerciseId(exerciseId);
            submitBean.setExerciseType(exerciseType);
            submitBean.setQuestionId(exerciseItemBean.getQuestionId());
            submitBean.setQuestionType(exerciseItemBean.getQuestionType());
            submitBean.setUserAnswer(exerciseItemBean.getUserAnswer());
            submitBean.setUserAnswerImg(exerciseItemBean.getUserAnswerImg());
            submitBean.setCouId(couId);
            submitBean.setTrainingId(mTrainingCampId);
            submitBean.setNodeTaskLinkId(mNoteTaskId);
            submitBeanList.add(submitBean);
        }

        if (isAllUnanswered) {
            showMessage("请先填写一条作业内容");
        } else if (!isHasUnanswered) {
            DialogUtils.showCancelSureDialog(this, "提交作业", "尚未写完作业，确认提交？",
                    "取消", "确认", new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickSure() {
                            submitUserAnswer(submitBeanList);
                        }
                    });
        } else {
            submitUserAnswer(submitBeanList);
        }
    }

    /**
     * 修改作业 只提交修改的简答题的内容
     */
    private void submitUpdateAnswer() {
        //提交答案集合
        List<ExerciseSubmitBean> submitBeanList = new ArrayList<>();
        //是否一题没答 (只要有答案就置为false)
        boolean isAllUnanswered = true;

        List<ExerciseItemBean> itemBeanList = mDoAdapter.getData();
        for (int i = 0; i < itemBeanList.size(); i++) {
            ExerciseItemBean exerciseItemBean = itemBeanList.get(i);
            StringBuilder sb = new StringBuilder();
            //试题类型：1.单选；2.多选；3.判断；4.不定项；5.简答题；6.论述题
            int questionType = exerciseItemBean.getQuestionType();
            if (questionType == 5 || questionType == 6) {
                List<FeedbackPictureBean> pictureBeans = exerciseItemBean.getUserChoseImg();
                if (pictureBeans != null) {
                    for (int j = 0; j < pictureBeans.size(); j++) {
                        if (!TextUtils.isEmpty(pictureBeans.get(j).getAbsolutePath())) {
                            sb.append(pictureBeans.get(j).getAbsolutePath()).append(",");
                        }
                    }
                }
                exerciseItemBean.setUserAnswerImg(StringUtil.subStrEnd(sb.toString()));

                ExerciseSubmitBean submitBean = new ExerciseSubmitBean();
                submitBean.setUserAnswerRecordId(exerciseItemBean.getUserAnswerRecordId());
                submitBean.setExerciseId(exerciseId);
                submitBean.setExerciseType(exerciseType);
                submitBean.setQuestionId(exerciseItemBean.getQuestionId());
                submitBean.setQuestionType(exerciseItemBean.getQuestionType());
                submitBean.setUserAnswer(TextUtils.isEmpty(exerciseItemBean.getUserAnswer()) ? "" : exerciseItemBean.getUserAnswer());
                submitBean.setUserAnswerImg(TextUtils.isEmpty(exerciseItemBean.getUserAnswerImg()) ? "" : exerciseItemBean.getUserAnswerImg());
                submitBean.setCouId(couId);
                submitBean.setTrainingId(mTrainingCampId);
                submitBean.setNodeTaskLinkId(mNoteTaskId);
                submitBeanList.add(submitBean);
            }

            if (!TextUtils.isEmpty(exerciseItemBean.getUserAnswer()) && isAllUnanswered) {
                //有答案
                isAllUnanswered = false;
            }
        }

        if (isAllUnanswered) {
            showMessage("请先填写一条作业内容");
        } else {
            submitUserAnswer(submitBeanList);
        }
    }

    private void submitUserAnswer(List<ExerciseSubmitBean> submitBeanList) {
        if (mPresenter != null) {
            mPresenter.submitExerciseAnswer(submitBeanList);
        }
    }

    @Override
    public void showLoading() {
        if (mTvSubmit != null) {
            mTvSubmit.setClickable(false);
        }
    }

    @Override
    public void hideLoading() {
        if (mTvSubmit != null) {
            mTvSubmit.setClickable(true);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadingDialog.getInstance().dismiss();
    }

}
