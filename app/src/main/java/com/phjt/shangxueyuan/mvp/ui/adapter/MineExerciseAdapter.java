package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.view.roundView.RoundTextView;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/3/17
 * company: 普华集团
 * description:我的作业
 */
public class MineExerciseAdapter extends BaseQuickAdapter<MineExerciseBean, BaseViewHolder> {

    private Context mContext;
    private int mType;

    public MineExerciseAdapter(Context context, List<MineExerciseBean> data) {
        super(R.layout.item_my_exercise,data);
        this.mContext = context;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MineExerciseBean item) {
        helper.setText(R.id.tv_audition_title_item, item.getExerciseName())
                .setText(R.id.tv_audition_content_item, String.format(mContext.getString(R.string.cou_name), item.getCouName()));
        RoundTextView tvPunchClock = helper.getView(R.id.tv_has_been_removed);

        if (0 == item.getState()) {
            tvPunchClock.setText("未提交");
            tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_2076FF));
            tvPunchClock.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else if (1 == item.getState()) {
            tvPunchClock.setText("已提交");
            tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_E6E6E6));
            tvPunchClock.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
        } else if (2 == item.getState()) {
            tvPunchClock.setText("已点评");
            tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_662475FF));
            tvPunchClock.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }
}
