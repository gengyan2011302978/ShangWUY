package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundView.RoundTextView;

/**
 * @author: Roy
 * date:   2021/1/15
 * company: 普华集团
 * description:参与的打卡的Adapter
 */
public class ParticipatingPunchAdapter extends BaseQuickAdapter<ParticipatingPunchBean, BaseViewHolder> {

    private Context mContext;

    public ParticipatingPunchAdapter(Context context) {
        super(R.layout.item_participating_punch);
        this.mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ParticipatingPunchBean item) {

        helper.setText(R.id.tv_audition_title_item, item.getPunchCardName())
                .setText(R.id.tv_punch_clock, "打卡")
                .setText(R.id.tv_number_times,"");
        ImageView ivTutor = helper.getView(R.id.iv_participating_punch);
        GlideUtils.load(item.getCoverImg(), ivTutor, R.drawable.image_placeholder);

        RoundTextView tvPunchClock = helper.getView(R.id.tv_punch_clock);
        tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4071FC));
        tvPunchClock.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        // 0未参与 1打卡 2已打卡 3结束
        if (0 == item.getPunchStatus()) {
            helper.setVisible(R.id.tv_number_times, true)
                    .setVisible(R.id.tv_number, false)
                    .setVisible(R.id.tv_number_data, false)
                    .setVisible(R.id.tv_study_day, false)
                    .setText(R.id.tv_number_times, String.format("开始日期:%s", item.getPunchCardStartDate()))
                    .setText(R.id.tv_punch_clock, "去参与");
        } else if (1 == item.getPunchStatus()) {
            helper.setVisible(R.id.tv_number_times, true)
                    .setVisible(R.id.tv_number, true)
                    .setVisible(R.id.tv_number_data, true)
                    .setVisible(R.id.tv_study_day, true)
                    .setText(R.id.tv_number_times, "打卡")
                    .setText(R.id.tv_number, String.format("%s次", item.getPunchNum()))
                    .setText(R.id.tv_study_day, String.format("%s天", item.getDays()));

        } else if (2 == item.getPunchStatus()) {
            helper.setVisible(R.id.tv_number_times, true)
                    .setVisible(R.id.tv_number, true)
                    .setVisible(R.id.tv_number_data, true)
                    .setVisible(R.id.tv_study_day, true)
                    .setText(R.id.tv_number, String.format("%s次", item.getPunchNum()))
                    .setText(R.id.tv_number_times, "打卡")
                    .setText(R.id.tv_study_day, String.format("%s天", item.getDays()))
                    .setText(R.id.tv_punch_clock, "已打卡");
            tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_662475FF));

        } else if (3 == item.getPunchStatus()) {
            helper.setVisible(R.id.tv_number_times, true)
                    .setVisible(R.id.tv_number, false)
                    .setVisible(R.id.tv_number_data, false)
                    .setVisible(R.id.tv_study_day, false)
                    .setText(R.id.tv_number_times, String.format("打卡天数:%s", item.getPunchNum() + "/" + item.getTotalNum()))
                    .setText(R.id.tv_punch_clock, "已结束");
            tvPunchClock.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_E6E6E6));
            tvPunchClock.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
        }
    }
}
