package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseItemBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseListActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseShowActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.zzhoujay.richtext.RichText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExerciseShowQuestionItemAdapter extends BaseQuickAdapter<ExerciseItemBean, BaseViewHolder> {

    private Context mContext;
    List<ExerciseItemBean> exerciseItemBeans = new ArrayList<>();

    /**
     * 是否是训练营 true是
     */
    private boolean isTrainingCamp;

    public void setTrainingCamp(boolean trainingCamp) {
        isTrainingCamp = trainingCamp;
    }

    public ExerciseShowQuestionItemAdapter(Context context, @Nullable List<ExerciseItemBean> data) {
        super(R.layout.item_exercise_show_chose, data);
        this.exerciseItemBeans = data;
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExerciseItemBean item) {
        TextView tvTitleContentItem = helper.getView(R.id.tv_title_content_item);
        ImageView ivExpand = helper.getView(R.id.iv_expand);
        TextView tvExpand = helper.getView(R.id.tv_expand);
        LinearLayout linearExpand = helper.getView(R.id.linear_expand);
        LinearLayout linearOther = helper.getView(R.id.linear_other);
        ConstraintLayout constrFooter = helper.getView(R.id.constr_footer);
        helper.addOnClickListener(R.id.linear_expand);
        if (exerciseItemBeans.size() > 1) {
            if (item.getNumbType() == 2) {
                if (helper.getAdapterPosition() + 1 == exerciseItemBeans.size()) {
                    linearExpand.setVisibility(View.VISIBLE);
                    constrFooter.setVisibility(View.VISIBLE);
                } else {
                    linearExpand.setVisibility(View.GONE);
                    constrFooter.setVisibility(View.GONE);
                }
                linearOther.setVisibility(View.VISIBLE);
                ivExpand.setBackgroundResource(R.drawable.icon_exclose);
                tvExpand.setText("收起");
                tvExpand.setTextColor(ContextCompat.getColor(mContext, R.color.color_707B91));
            } else {
                ivExpand.setBackgroundResource(R.drawable.icon_expand);
                tvExpand.setText("展开");
                tvExpand.setTextColor(ContextCompat.getColor(mContext, R.color.color_387BFE));
                if (helper.getAdapterPosition() == 0) {
                    linearOther.setVisibility(View.VISIBLE);
                    linearExpand.setVisibility(View.VISIBLE);
                    constrFooter.setVisibility(View.VISIBLE);
                } else {
                    linearOther.setVisibility(View.GONE);
                    linearExpand.setVisibility(View.GONE);
                    constrFooter.setVisibility(View.GONE);
                }
            }
        } else {
            linearExpand.setVisibility(View.GONE);
        }
        String str = "";
        if (item.getQuestionType() == 1) {
            str = ".(单选题)";
        } else if (item.getQuestionType() == 2) {
            str = ".(多选题)";
        } else if (item.getQuestionType() == 3) {
            str = ".(判断题)";
        } else if (item.getQuestionType() == 4) {
            str = ".(不定项题)";
        } else if (item.getQuestionType() == 5) {
            str = ".(简答题)";
        } else if (item.getQuestionType() == 6) {
            str = ".(论述题)";
        } else {
            str = ".(简答题)";
        }
        RichText.from("<span></span>" + (helper.getPosition() + 1) + str + item.getQuestionContent()).into(tvTitleContentItem);
        RecyclerView rvOptionItem = helper.getView(R.id.rv_option_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvOptionItem.setLayoutManager(layoutManager);
        ExerciseShowAnswerItemAdapter adapter = new ExerciseShowAnswerItemAdapter(mContext, item.getOptionVos());
        rvOptionItem.setAdapter(adapter);

        Group groupExercise = helper.getView(R.id.group_exercise);
        groupExercise.setVisibility(isTrainingCamp ? View.GONE : View.VISIBLE);

        TextView tvExerciseNext = helper.getView(R.id.tv_exercise_next);
        TextView tvExerciseLast = helper.getView(R.id.tv_exercise_last);
        TextView tvExerciseCourse = helper.getView(R.id.tv_exercise_course);
        tvExerciseCourse.setText("相关课程：" + ExerciseShowActivity.couName);
        if (item.getPosition() == 0) {
            tvExerciseLast.setTextColor(ContextCompat.getColor(mContext, R.color.color_DCDCDC));
            if (item.getCommentBeans() != null && item.getCommentBeans().size() > 0) {
                if (1 == item.getCommentBeans().size()) {
                    tvExerciseLast.setTextColor(ContextCompat.getColor(mContext, R.color.color_DCDCDC));
                }
            }
        }
        if (item.getCommentBeans() == null || item.getPosition() + 1 == item.getCommentBeans().size()) {
            tvExerciseNext.setTextColor(ContextCompat.getColor(mContext, R.color.color_DCDCDC));
        }

        tvExerciseCourse.setOnClickListener(v -> {
            if (mContext instanceof Activity) {
                Activity activity = (Activity) mContext;
                activity.finish();
                ExerciseListActivity.instance.finish();
            }
        });

        tvExerciseLast.setOnClickListener(v -> {
            if (item.getCommentBeans() != null && item.getPosition() > 0) {
                MineExerciseBean exerciseBean = item.getCommentBeans().get(item.getPosition() - 1);
                Intent intent = new Intent(mContext, ExerciseShowActivity.class);
                intent.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseBean.getId());
                intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBean.getExerciseBookId());
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, exerciseBean.getCouId());
                intent.putExtra("commentBeans", (Serializable) item.getCommentBeans());
                mContext.startActivity(intent);
                if (mContext instanceof Activity) {
                    Activity activity = (Activity) mContext;
                    activity.finish();
                }
            }
        });
        tvExerciseNext.setOnClickListener(v -> {
            if (item.getCommentBeans() != null && item.getPosition() < item.getCommentBeans().size() - 1) {
                MineExerciseBean exerciseBean = item.getCommentBeans().get(item.getPosition() + 1);
                Intent intent = new Intent(mContext, ExerciseShowActivity.class);
                intent.putExtra(Constant.BUNDLE_EXERCISE_ID, exerciseBean.getId());
                intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, exerciseBean.getExerciseBookId());
                intent.putExtra(Constant.PUNCH_CARDS_COURSE_ID, exerciseBean.getCouId());
                intent.putExtra("commentBeans", (Serializable) item.getCommentBeans());
                mContext.startActivity(intent);
                if (mContext instanceof Activity) {
                    Activity activity = (Activity) mContext;
                    activity.finish();
                }
            }
        });
    }
}
