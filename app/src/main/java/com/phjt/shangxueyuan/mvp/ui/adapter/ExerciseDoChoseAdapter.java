package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseItemChoseBean;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author: gengyan
 * date:    2021/3/16 10:55
 * company: 普华集团
 * description: 写作业
 */
public class ExerciseDoChoseAdapter extends BaseQuickAdapter<ExerciseItemChoseBean, BaseViewHolder> {

    private Context mContext;

    /**
     * 题类型：1.单选；2.多选；3.判断；4.不定项；5.简答题；6.论述题
     */
    private int questionType;
    /**
     * 作业状态值 0未提交 1已提交 2已点评 (4修改作业)
     */
    private int state;
    /**
     * 正确答案
     */
    private String rightAnswer;
    /**
     * 用户选择
     */
    private String userAnswer;

    public ExerciseDoChoseAdapter(Context context, @Nullable List<ExerciseItemChoseBean> data) {
        super(R.layout.item_exercise_item_chose, data);
        this.mContext = context;
    }

    public void setQuestionTypeAndState(int questionType, int state) {
        this.questionType = questionType;
        this.state = state;
    }

    public void setRightAndUserAnswer(String rightAnswer, String userAnswer) {
        this.rightAnswer = rightAnswer;
        this.userAnswer = userAnswer;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, ExerciseItemChoseBean item) {
        int position = helper.getLayoutPosition();
        ImageView ivChoseItem = helper.getView(R.id.iv_chose_item);
        TextView tvChoseItem = helper.getView(R.id.tv_chose_item);
        if (questionType == 3) {
            tvChoseItem.setText(item.getOptionContent());
        } else {
            tvChoseItem.setText(item.getOptionName() + "." + item.getOptionContent());
        }

        boolean isChose = item.isChose();
        if (state == 0) {
            if (isChose) {
                setChoseRight(ivChoseItem, tvChoseItem);
            } else {
                setNoChose(ivChoseItem, tvChoseItem);
            }
        } else {
            if (!TextUtils.isEmpty(userAnswer) &&
                    Arrays.asList(userAnswer.split(",")).contains(item.getOptionId())) {
                //用户选择 判断是否正确
                if (!TextUtils.isEmpty(rightAnswer) &&
                        Arrays.asList(rightAnswer.split(",")).contains(item.getOptionId())) {
                    //正确答案
                    setChoseRight(ivChoseItem, tvChoseItem);
                } else {
                    setChoseWrong(ivChoseItem, tvChoseItem);
                }
            } else {
                //用户未选
                if (questionType == 1 && !TextUtils.isEmpty(rightAnswer) &&
                        Arrays.asList(rightAnswer.split(",")).contains(item.getOptionId())) {
                    //单选题，用户未选择时，显示正确答案
                    setChoseRight(ivChoseItem, tvChoseItem);
                } else {
                    setNoChose(ivChoseItem, tvChoseItem);
                }
            }
        }

        helper.getView(R.id.cl_chose_item).setOnClickListener(v -> {
            if (state == 0) {
                if (questionType == 1 || questionType == 3) {
                    singleChoseReset(position);
                } else {
                    item.setChose(!isChose);
                }
                notifyDataSetChanged();
            } else if (state == 4) {
                TipsUtil.show("客观题答案提交后不能再改了哦");
            }
        });
    }

    private void setNoChose(ImageView ivChose, TextView tvChose) {
        if (questionType == 1 || questionType == 3) {
            ivChose.setImageResource(R.drawable.chose_single);
        } else if (questionType == 2 || questionType == 4) {
            ivChose.setImageResource(R.drawable.chose_multiple);
        }
        tvChose.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
    }

    private void setChoseRight(ImageView ivChose, TextView tvChose) {
        if (questionType == 1) {
            ivChose.setImageResource(R.drawable.chose_single_right);
        } else if (questionType == 2 || questionType == 4) {
            ivChose.setImageResource(R.drawable.chose_multiple_right);
        } else if (questionType == 3) {
            ivChose.setImageResource(R.drawable.chose_judgment_right);
        }
        tvChose.setTextColor(ContextCompat.getColor(mContext, R.color.color_2E7DFF));
    }

    private void setChoseWrong(ImageView ivChose, TextView tvChose) {
        if (questionType == 1) {
            ivChose.setImageResource(R.drawable.chose_single_wrong);
        } else if (questionType == 2 || questionType == 4) {
            ivChose.setImageResource(R.drawable.chose_multiple_wrong);
        } else if (questionType == 3) {
            ivChose.setImageResource(R.drawable.chose_judgment_wrong);
        }
        tvChose.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF575D));
    }

    /**
     * 单选，重置选项并选中
     *
     * @param position 选中的position
     */
    private void singleChoseReset(int position) {
        List<ExerciseItemChoseBean> itemChoseBeans = getData();
        for (int i = 0; i < itemChoseBeans.size(); i++) {
            ExerciseItemChoseBean itemChoseBean = itemChoseBeans.get(i);
            itemChoseBean.setChose(position == i);
        }
    }
}
