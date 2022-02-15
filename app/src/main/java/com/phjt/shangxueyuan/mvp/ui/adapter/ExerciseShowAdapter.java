package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

import static com.phjt.shangxueyuan.mvp.ui.activity.ExerciseShowActivity.couId;

public class ExerciseShowAdapter extends BaseMultiItemQuickAdapter<ExerciseShowBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private Context mContext;
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * 是否是训练营 true是
     */
    private boolean isTrainingCamp;

    public void setTrainingCamp(boolean trainingCamp) {
        isTrainingCamp = trainingCamp;
    }

    public ExerciseShowAdapter(Context context, List<ExerciseShowBean> data) {
        super(data);
        this.mContext = context;

        addItemType(TYPE_LEVEL_0, R.layout.item_exercise_show);
        addItemType(TYPE_LEVEL_1, R.layout.item_exericse_show_answer);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExerciseShowBean item) {

        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                RecyclerView rvOption = helper.getView(R.id.rv_option);
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rvOption.setLayoutManager(layoutManager);
                ExerciseShowQuestionItemAdapter adapter = new ExerciseShowQuestionItemAdapter(mContext, item.getQuestionVoList());
                adapter.setTrainingCamp(isTrainingCamp);
                rvOption.setAdapter(adapter);
                adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                    switch (view.getId()) {
                        case R.id.linear_expand:
                            for (int i = 0; i < item.getQuestionVoList().size(); i++) {
                                if (item.getQuestionVoList().get(i).getNumbType() == 2) {
                                    item.getQuestionVoList().get(i).setNumbType(1);
                                } else {
                                    item.getQuestionVoList().get(i).setNumbType(2);
                                }
                            }
                            adapter1.notifyDataSetChanged();
                            break;
                        case R.id.account_number:
                            Intent intent = new Intent(mContext, CourseDetailActivity.class);
                            intent.putExtra(Constant.BUNDLE_COURSE_ID, couId);
                            mContext.startActivity(intent);
                            if (mContext instanceof Activity) {
                                Activity activity = (Activity) mContext;
                                activity.finish();
                            }
                        default:
                            break;
                    }
                });
                break;
            case TYPE_LEVEL_1:
                RoundedImageView ivAnswerIcon = helper.getView(R.id.iv_answer_icon);
                AppImageLoader.loadResUrl(item.getPhoto(), ivAnswerIcon, R.drawable.iv_mine_avatar);
                TextView tvAnswerNameItem = helper.getView(R.id.tv_answer_name_item);
                TextView tvAnswerChoseItem = helper.getView(R.id.tv_answer_chose_item);
                TextView tvTimeItem = helper.getView(R.id.tv_time_item);
                TextView tvAnswerTitleTtem = helper.getView(R.id.tv_answer_title_item);
                TextView tvZanCountItem = helper.getView(R.id.tv_zan_count_item);
                TextView tvPublicDisplay = helper.getView(R.id.tv_public_display);
                TextView tvLookAnswer = helper.getView(R.id.tv_look_answer);
                ImageView ivAlread = helper.getView(R.id.iv_alread);
                ImageView ivZanItem = helper.getView(R.id.iv_zan_item);
                helper.addOnClickListener(R.id.tv_look_answer);
                helper.addOnClickListener(R.id.tv_public_display);
                helper.addOnClickListener(R.id.iv_zan_item);
                helper.addOnClickListener(R.id.tv_zan_count_item);

                ivZanItem.setImageResource("0".equals(item.getLikeState()) ? R.drawable.ic_like_un : R.drawable.ic_like);
                tvZanCountItem.setText(item.getLikeNum() == 0 ? "赞" : item.getLikeNum() + "");
                if (item.getAnwserType() == 1) {
                    tvAnswerTitleTtem.setText("我的答案");
                    tvLookAnswer.setVisibility(View.VISIBLE);
                    tvPublicDisplay.setVisibility(View.VISIBLE);
                    tvAnswerTitleTtem.setVisibility(View.VISIBLE);
                    if (item.getState() == 2) {
                        ivAlread.setVisibility(View.VISIBLE);
                        tvLookAnswer.setText("查看解析");
                        if (item.getExerciseRecordState() == 0) {
                            tvPublicDisplay.setText("公开展示");
                        } else {
                            tvPublicDisplay.setText("取消公开");
                        }
                    } else {
                        ivAlread.setVisibility(View.GONE);
                        tvLookAnswer.setText("编辑");
                        tvPublicDisplay.setText("查看解析");
                    }
                } else if (item.getAnwserType() == 2) {
                    ivAlread.setVisibility(View.VISIBLE);
                    tvLookAnswer.setVisibility(View.GONE);
                    tvPublicDisplay.setVisibility(View.GONE);
                    tvAnswerTitleTtem.setText("同学作业");
                    tvAnswerTitleTtem.setVisibility(View.VISIBLE);
                } else {
                    tvAnswerTitleTtem.setVisibility(View.GONE);
                    tvLookAnswer.setVisibility(View.GONE);
                    tvPublicDisplay.setVisibility(View.GONE);
                }
                tvAnswerNameItem.setText(item.getNickName());
                String answerStr = "";
                for (int i = 0; i < item.getRecordVos().size(); i++) {
                    if (null != item.getRecordVos().get(i)) {
                        if ("0".equals(String.valueOf(item.getRecordVos().get(i).getQuestionType())) || "null".equals(String.valueOf(item.getRecordVos().get(i).getQuestionType())) || item.getRecordVos().get(i).getQuestionType() <= 0 || item.getRecordVos().get(i).getQuestionType() == 5) {
                            if (null != item.getRecordVos().get(i).getUserAnswer()) {
                                if (item.getRecordVos().get(i).getUserAnswer().contains(".")) {
                                    RecyclerView rvAnswerAnalysistItem = helper.getView(R.id.rv_answer_analysist_item);
                                    LinearLayoutManager layoutManagerImg = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                                    rvAnswerAnalysistItem.setLayoutManager(layoutManagerImg);
                                    AnswerImagetAdapter answerImageAdapter = new AnswerImagetAdapter(mContext, item.getRecordVos());
                                    rvAnswerAnalysistItem.setAdapter(answerImageAdapter);
                                } else {
                                    RecyclerView rvAnswerAnalysistItem = helper.getView(R.id.rv_answer_analysist_item);
                                    LinearLayoutManager layoutManagerImg = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                                    rvAnswerAnalysistItem.setLayoutManager(layoutManagerImg);
                                    if (!"".equals(item.getRecordVos().get(i).getUserAnswer())) {
                                        item.getRecordVos().get(i).setUserAnswer((i + 1) + "." + item.getRecordVos().get(i).getUserAnswer());
                                    } else {
                                        if (null != item.getRecordVos().get(i).getUserAnswerImg()) {
                                            if (!"".equals(item.getRecordVos().get(i).getUserAnswerImg())) {
                                                item.getRecordVos().get(i).setUserAnswer((i + 1) + "." + item.getRecordVos().get(i).getUserAnswer());
                                            } else {
                                                item.getRecordVos().get(i).setUserAnswer((i + 1) + "." +
                                                        (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ");
                                            }
                                        } else {
                                            item.getRecordVos().get(i).setUserAnswer((i + 1) + "." +
                                                    (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ");
                                        }
                                    }
                                    AnswerImagetAdapter answerImageAdapter = new AnswerImagetAdapter(mContext, item.getRecordVos());
                                    rvAnswerAnalysistItem.setAdapter(answerImageAdapter);
                                }
                            }
                        } else if (item.getRecordVos().get(i).getQuestionType() == 6) {
                            if (null != item.getRecordVos().get(i).getUserAnswer()) {
                                if (item.getRecordVos().get(i).getUserAnswer().contains(".")) {
                                    RecyclerView rvAnswerAnalysisItem = helper.getView(R.id.rv_answer_analysis_item);
                                    LinearLayoutManager layoutManagerImg = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                                    rvAnswerAnalysisItem.setLayoutManager(layoutManagerImg);
                                    AnswerImageAdapter answerImageAdapter = new AnswerImageAdapter(mContext, item.getRecordVos());
                                    rvAnswerAnalysisItem.setAdapter(answerImageAdapter);
                                } else {
                                    RecyclerView rvAnswerAnalysisItem = helper.getView(R.id.rv_answer_analysis_item);
                                    LinearLayoutManager layoutManagerImg = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                                    rvAnswerAnalysisItem.setLayoutManager(layoutManagerImg);
                                    if (!"".equals(item.getRecordVos().get(i).getUserAnswer())) {
                                        item.getRecordVos().get(i).setUserAnswer((i + 1) + "." + item.getRecordVos().get(i).getUserAnswer());
                                    } else {
                                        if (null != item.getRecordVos().get(i).getUserAnswerImg()) {
                                            if (!"".equals(item.getRecordVos().get(i).getUserAnswerImg())) {
                                                item.getRecordVos().get(i).setUserAnswer((i + 1) + "." + item.getRecordVos().get(i).getUserAnswer());
                                            } else {
                                                item.getRecordVos().get(i).setUserAnswer((i + 1) + "." +
                                                        (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ");
                                            }
                                        } else {
                                            item.getRecordVos().get(i).setUserAnswer((i + 1) + "." +
                                                    (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ");
                                        }
                                    }
                                    AnswerImageAdapter answerImageAdapter = new AnswerImageAdapter(mContext, item.getRecordVos());
                                    rvAnswerAnalysisItem.setAdapter(answerImageAdapter);
                                }
                            }
                        } else {
                            if (item.getRecordVos().get(i).getQuestionType() == 3) {

                                for (int j = 0; j < item.getRecordVos().get(i).getOptionVos().size(); j++) {
                                    if (null != item.getRecordVos().get(i).getUserAnswer() && item.getRecordVos().get(i).getUserAnswer().equals(item.getRecordVos().get(i).getOptionVos().get(j).getOptionId())) {
                                        if (item.getRecordVos().get(i).getUserAnswer().equals(item.getRecordVos().get(i).getRightShoice())) {
                                            answerStr = answerStr + (i + 1) + "." + item.getRecordVos().get(i).getOptionVos().get(j).getOptionContent() + "   ";
                                            break;
                                        } else {
                                            answerStr = answerStr + (i + 1) + "." + (isTrainingCamp ? "<font color= \"#666666\">" : "<font color= \"#ff575d\">")
                                                    + item.getRecordVos().get(i).getOptionVos().get(j).getOptionContent() + "</font>" + "   ";
                                            break;
                                        }
                                    } else {
                                        if (null == item.getRecordVos().get(i).getUserAnswer() || "".equals(item.getRecordVos().get(i).getUserAnswer())) {
                                            answerStr = answerStr + (i + 1) + "." + (isTrainingCamp ? "<font color= \"#666666\">" : "<font color= \"#ff575d\">")
                                                    + "未答题" + "</font>" + "   ";
                                            break;
                                        }
                                    }
                                }
                            } else {
                                if (null != item.getRecordVos().get(i).getOptionVos()) {
                                    String answerStrT = "";
                                    String answerStrColor = "";
                                    if (item.getRecordVos().get(i).getOptionVos().size() == 0) {
                                        answerStr = answerStr + (i + 1) + "." +
                                                (isTrainingCamp ? "<font color= \"#666666\">" : "<font color= \"#ff575d\">") + "未答题</font>" + "   ";
                                    }
                                    for (int j = 0; j < item.getRecordVos().get(i).getOptionVos().size(); j++) {
                                        if (!TextUtils.isEmpty(item.getRecordVos().get(i).getUserAnswer())) {
                                            if (item.getRecordVos().get(i).getUserAnswer().contains(item.getRecordVos().get(i).getOptionVos().get(j).getOptionId())) {
                                                if (item.getRecordVos().get(i).getUserAnswer().equals(item.getRecordVos().get(i).getRightShoice())) {
                                                    answerStrColor = "<font color= \"#666666\">";
                                                } else {
                                                    answerStrColor = isTrainingCamp ? "<font color= \"#666666\">" : "<font color= \"#ff575d\">";
                                                }
                                                answerStrT = answerStrT + answerStrColor + item.getRecordVos().get(i).getOptionVos().get(j).getOptionName() + "</font>" + "   ";
                                            }
                                        }
                                    }
                                    if ("".equals(answerStrT)) {
                                        answerStrT = answerStrT +
                                                (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ";
                                    }
                                    answerStr = answerStr + (i + 1) + "." + answerStrT + "   ";
                                } else {
                                    answerStr = answerStr + (i + 1) + "." +
                                            (isTrainingCamp ? "<font color= \"#666666\">未答题</font>" : "<font color= \"#ff575d\">未答题</font>") + "   ";
                                }
                            }
                        }
                    }
                }
                tvAnswerChoseItem.setText(Html.fromHtml(answerStr));
                tvTimeItem.setText(item.getAddTime());
            default:
                break;
        }
    }
}
