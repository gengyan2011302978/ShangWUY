package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseItemBean;
import com.phjt.shangxueyuan.bean.ExerciseItemChoseBean;
import com.phjt.shangxueyuan.bean.FeedbackPictureBean;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.StringUtil;
import com.phjt.shangxueyuan.widgets.TagTextView;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.phjt.base.utils.ArchitectUtils.startActivity;

/**
 * @author: gengyan
 * date:    2021/3/16 10:55
 * company: 普华集团
 * description: 写作业
 */
public class ExerciseDoAdapter extends BaseMultiItemQuickAdapter<ExerciseItemBean, BaseViewHolder> {

    private Context mContext;
    /**
     * 作业状态值 0未提交 1已提交 2已点评  (4修改作业)
     */
    private int state;

    public void setState(int state) {
        this.state = state;
    }

    public ExerciseDoAdapter(Context context, @Nullable List<ExerciseItemBean> data) {
        super(data);
        this.mContext = context;
        addItemType(ExerciseItemBean.EXERCISE_TYPE_CHOSE, R.layout.item_exercise_do_chose);
        addItemType(ExerciseItemBean.EXERCISE_TYPE_ANALYSIS, R.layout.item_exercise_do_analysis);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(BaseViewHolder helper, ExerciseItemBean item) {
        int position = helper.getLayoutPosition();
        int orderNum = position + 1 - getHeaderLayoutCount();
        //试题类型：1.单选；2.多选；3.判断；4.不定项；5.简答题；6.论述题
        int questionType = item.getQuestionType();

        switch (helper.getItemViewType()) {
            case ExerciseItemBean.EXERCISE_TYPE_CHOSE:
                helper.setText(R.id.tv_order_num_item, orderNum < 10 ? "0" + orderNum : String.valueOf(orderNum));
                TagTextView tagTextView = helper.getView(R.id.tv_title_content_item);
                String contentTitle = "  " + item.getQuestionContent();
                if (questionType == 1) {
                    tagTextView.setContentAndTag(contentTitle, "单选题", ContextCompat.getColor(mContext, R.color.color_5ECAE8));
                } else if (questionType == 2) {
                    tagTextView.setContentAndTag(contentTitle, "多选题", ContextCompat.getColor(mContext, R.color.color_387BFE));
                } else if (questionType == 3) {
                    tagTextView.setContentAndTag(contentTitle, "判断题", ContextCompat.getColor(mContext, R.color.color_FF8A55));
                } else if (questionType == 4) {
                    tagTextView.setContentAndTag(contentTitle, "不定项选择题", ContextCompat.getColor(mContext, R.color.color_387BFE));
                }

                RecyclerView rvOption = helper.getView(R.id.rv_option_item);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rvOption.setLayoutManager(layoutManager);
                List<ExerciseItemChoseBean> choseBeanList = item.getOptionVos();
                ExerciseDoChoseAdapter doChoseAdapter = new ExerciseDoChoseAdapter(mContext, choseBeanList);
                doChoseAdapter.setQuestionTypeAndState(questionType, state);
                doChoseAdapter.setRightAndUserAnswer(item.getRightShoice(), item.getUserAnswer());
                rvOption.setAdapter(doChoseAdapter);

                TextView tvChoseResult = helper.getView(R.id.tv_chose_result_item);
                TextView tvRightAnswerTip = helper.getView(R.id.right_answer_item);
                TextView tvRightAnswerContent = helper.getView(R.id.tv_right_answer_item);
                if (state == 0) {
                    tvChoseResult.setVisibility(View.GONE);
                    tvRightAnswerTip.setVisibility(View.GONE);
                    tvRightAnswerContent.setVisibility(View.GONE);
                } else {
                    tvChoseResult.setVisibility(View.VISIBLE);
                    //是否正确 0错 1对
                    int answerState = item.getAnswerState();
                    if (answerState == 1) {
                        tvChoseResult.setText("回答正确");
                        tvChoseResult.setTextColor(ContextCompat.getColor(mContext, R.color.color_30D989));
                        tvRightAnswerTip.setVisibility(View.GONE);
                        tvRightAnswerContent.setVisibility(View.GONE);
                    } else {
                        tvChoseResult.setText(TextUtils.isEmpty(item.getUserAnswer()) ? "学员暂未完成这道题" : "回答错误");
                        tvChoseResult.setTextColor(ContextCompat.getColor(mContext, R.color.color_F25257));
                        if (questionType == 3) {
                            //判断题不显示
                            tvRightAnswerTip.setVisibility(View.GONE);
                            tvRightAnswerContent.setVisibility(View.GONE);
                        } else {
                            tvRightAnswerTip.setVisibility(View.VISIBLE);
                            tvRightAnswerContent.setVisibility(View.VISIBLE);

                            String rightShoice = item.getRightShoice();
                            StringBuilder sb = new StringBuilder();
                            if (!TextUtils.isEmpty(rightShoice)) {
                                List<String> rightChoseList = Arrays.asList(rightShoice.split(","));
                                List<ExerciseItemChoseBean> choseBeans = item.getOptionVos();
                                if (choseBeans != null) {
                                    for (int i = 0; i < choseBeans.size(); i++) {
                                        ExerciseItemChoseBean itemChoseBean = choseBeans.get(i);
                                        if (itemChoseBean != null && rightChoseList.contains(itemChoseBean.getOptionId())) {
                                            sb.append(itemChoseBean.getOptionName()).append("、");
                                        }
                                    }
                                }
                            }
                            tvRightAnswerContent.setText(StringUtil.subStrEnd(sb.toString()));
                        }
                    }
                }
                break;
            case ExerciseItemBean.EXERCISE_TYPE_ANALYSIS:
                helper.setText(R.id.tv_order_num_item, orderNum < 10 ? "0" + orderNum : String.valueOf(orderNum))
                        .setText(R.id.tv_tag_item, questionType == 5 ? "简答题" : "论述题");
                TextView tvAnalysisTitle = helper.getView(R.id.tv_analysis_title_item);
                RichText.from("<span>&#12288;&#12288;&#12288;&#8194;</span>" + item.getQuestionContent()).into(tvAnalysisTitle);

                Group fillInGroup = helper.getView(R.id.group_answer_fill_in);
                Group answerShowGroup = helper.getView(R.id.group_answer_show);

                if (state == 0 || state == 4) {
                    fillInGroup.setVisibility(View.VISIBLE);
                    answerShowGroup.setVisibility(View.GONE);

                    TextView tvAnswerContentCount = helper.getView(R.id.tv_answer_content_count);
                    EditText etAnswer = helper.getView(R.id.et_analysis_answer_item);
                    etAnswer.setOnFocusChangeListener((v, hasFocus) -> {
                        if (hasFocus) {
                            etAnswer.setOnTouchListener(onTouchListener);
                        } else {
                            etAnswer.setOnTouchListener(null);
                        }
                    });

                    MyWatcher myWatcherTag = (MyWatcher) etAnswer.getTag();
                    if (myWatcherTag != null) {
                        etAnswer.removeTextChangedListener(myWatcherTag);
                    }
                    MyWatcher myWatcher = new MyWatcher(item, tvAnswerContentCount);
                    etAnswer.addTextChangedListener(myWatcher);
                    etAnswer.setTag(myWatcher);
                    etAnswer.setText(item.getUserAnswer());

                    RecyclerView rvAnswerImg = helper.getView(R.id.rv_answer_img_item);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, LinearLayoutManager.VERTICAL, false);
                    rvAnswerImg.setLayoutManager(gridLayoutManager);

                    List<FeedbackPictureBean> userChoseImgList = item.getUserChoseImg();
                    if (userChoseImgList == null) {
                        userChoseImgList = new ArrayList<>();
                    }
                    if (state == 4 && !TextUtils.isEmpty(item.getUserAnswerImg())) {
                        List<String> imageList = Arrays.asList(item.getUserAnswerImg().split(","));
                        for (int i = 0; i < imageList.size(); i++) {
                            FeedbackPictureBean pictureBean = new FeedbackPictureBean();
                            pictureBean.setAbsolutePath(imageList.get(i));
                            userChoseImgList.add(pictureBean);
                        }
                        item.setUserAnswerImg(null);
                    }
                    if (userChoseImgList.size() < 9 && (userChoseImgList.isEmpty() || !TextUtils.isEmpty(userChoseImgList.get(userChoseImgList.size() - 1).getAbsolutePath()))) {
                        userChoseImgList.add(new FeedbackPictureBean());
                    }
                    item.setUserChoseImg(userChoseImgList);

                    NotesEditingAdapter imgAdapter = new NotesEditingAdapter(userChoseImgList);
                    rvAnswerImg.setAdapter(imgAdapter);
                    imgAdapter.setOnItemChildClickListener((adapter, view, index) -> {
                        switch (view.getId()) {
                            case R.id.iv_add:
                                if (iChoseAnswerImg != null) {
                                    iChoseAnswerImg.choseImg(position, imgAdapter);
                                }
                                break;
                            case R.id.iv_delete:
                                List<FeedbackPictureBean> pictureBeans = adapter.getData();
                                pictureBeans.remove(index);
                                if (!TextUtils.isEmpty(pictureBeans.get(pictureBeans.size() - 1).getAbsolutePath())) {
                                    //9张图片删除时，需手动添加一张空白图片
                                    pictureBeans.add(new FeedbackPictureBean());
                                }
                                adapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                    });
                } else {
                    fillInGroup.setVisibility(View.GONE);
                    answerShowGroup.setVisibility(View.VISIBLE);

                    helper.setText(R.id.tv_analysis_answer_item, item.getUserAnswer());
                    RecyclerView rvAnswerImg = helper.getView(R.id.rv_answer_img_show);
                    if (TextUtils.isEmpty(item.getUserAnswerImg())) {
                        rvAnswerImg.setVisibility(View.GONE);
                    } else {
                        rvAnswerImg.setVisibility(View.VISIBLE);

                        List<String> imageList = Arrays.asList(item.getUserAnswerImg().split(","));
                        GridLayoutManager gridLayoutManager;
                        if (imageList.size() == 4) {
                            gridLayoutManager = new GridLayoutManager(mContext, 2);
                        } else {
                            gridLayoutManager = new GridLayoutManager(mContext, 3);
                        }
                        rvAnswerImg.setLayoutManager(gridLayoutManager);
                        ThemeMainImageAdapter imageAdapter = new ThemeMainImageAdapter(imageList);
                        rvAnswerImg.setAdapter(imageAdapter);

                        imageAdapter.setOnItemClickListener((adapter, view, position1) -> {
                            List<String> adapterImages = adapter.getData();
                            Intent intent = new Intent(mContext, BigPhotoActivity.class);
                            intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(adapterImages));
                            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position1);
                            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                            intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                            startActivity(intent);
                        });
                    }

                    TextView tvTeacherComment = helper.getView(R.id.tv_teacher_comment_content);
                    if (TextUtils.isEmpty(item.getRemarkContent())) {
                        tvTeacherComment.setText("老师努力批阅中...");
                    } else {
                        RichText.from(item.getRemarkContent()).into(tvTeacherComment);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * EditText监听
     */
    static class MyWatcher implements TextWatcher {

        private ExerciseItemBean itemBean;
        private TextView tvAnswerContentCount;

        public MyWatcher(ExerciseItemBean itemBean, TextView tvAnswerContentCount) {
            this.itemBean = itemBean;
            this.tvAnswerContentCount = tvAnswerContentCount;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            itemBean.setUserAnswer(s.toString());

            String strContent = s.toString().trim();
            if (tvAnswerContentCount != null) {
                if (TextUtils.isEmpty(strContent)) {
                    tvAnswerContentCount.setText("0/10000");
                } else {
                    tvAnswerContentCount.setText(strContent.length() + "/10000");
                }
            }
        }
    }


    /**
     * 处理recycleview中edittext滑动冲突
     */
    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            } else if (MotionEvent.ACTION_UP == event.getAction()) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        }
    };


    private IChoseAnswerImg iChoseAnswerImg;

    public void setChoseAnswerImg(IChoseAnswerImg iChoseAnswerImg) {
        this.iChoseAnswerImg = iChoseAnswerImg;
    }

    public interface IChoseAnswerImg {
        /**
         * item中选择图片
         */
        void choseImg(int position, NotesEditingAdapter imgAdapter);
    }
}


