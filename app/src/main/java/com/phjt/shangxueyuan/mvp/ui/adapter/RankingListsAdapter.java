package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mzmedia.utils.String_Utils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.IntegralRankingBean;
import com.phjt.shangxueyuan.common.AppImageLoader;

/**
 * @author: Roy
 * date:   2020/10/21
 * company: 普华集团
 * description:
 */
public class RankingListsAdapter extends BaseQuickAdapter<IntegralRankingBean, BaseViewHolder> {

    private Context mContext;

    public RankingListsAdapter(Context context) {
        super(R.layout.item_ranking_lists);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, IntegralRankingBean item) {
        ImageView ivHead = helper.getView(R.id.iv_head);
        AppImageLoader.loadResUrl(item.getPhoto(), ivHead, R.drawable.iv_mine_avatar);
        TextView tvNumber = helper.getView(R.id.tv_number_item);
        if (helper.getAdapterPosition()+1 <= 3) {
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
                    break;
            }
        } else {
            helper.setVisible(R.id.iv_level_item, true);
            tvNumber.setTextColor(ContextCompat.getColor(mContext,R.color.color_FF666666));
        }
        helper.setText(R.id.tv_nickName, item.getNickName())
                .setText(R.id.tv_number_item, "NO." + String.valueOf(helper.getAdapterPosition() + 1))
                .setText(R.id.tv_credit_item, String_Utils.linearNmber(item.getUserBocc()));


    }
}
