package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.RankBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: gengyan
 * date:    2020/11/24 15:08
 * company: 普华集团
 * description: 描述
 */
public class RankListAdapter extends BaseQuickAdapter<RankBean.UserVosBean, BaseViewHolder> {

    private Context mContext;

    public RankListAdapter(Context context) {
        super(R.layout.item_ranking_clock);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RankBean.UserVosBean item) {
        helper.setText(R.id.tv_nickName,item.getNickName());
        helper.setText(R.id.tv_credit_item,item.getPartyNum()+"天");
        TextView tvNumber = helper.getView(R.id.tv_number_item);
//        if (helper.getAdapterPosition()+1 <= 3) {
        tvNumber.setText("No."+(helper.getAdapterPosition()+1));
            switch (helper.getAdapterPosition()+1) {
                case 1:
//                    helper.setImageResource(R.id.iv_level_item, R.drawable.icon_level_one);
                    tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FFE36460));
                    break;
                case 2:
//                    helper.setImageResource(R.id.iv_level_item, R.drawable.icon_level_two);
                    tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FFFF8500));
                    break;
                case 3:
//                    helper.setImageResource(R.id.iv_level_item, R.drawable.icon_level_three);
                    tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FF50E3C2));
                    break;
                default:
                    tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FF666666));
                    break;
            }
//        } else {
//            helper.setVisible(R.id.iv_level_item, true);
//            tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FF666666));
//        }

        RoundedImageView roundedImageView = helper.getView(R.id.iv_head);
        AppImageLoader.loadResUrl(item.getPhoto(),roundedImageView,R.drawable.iv_mine_avatar);
    }
}
