package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ScreenLabelBean;
import com.phjt.shangxueyuan.interf.IClickCallBack;

import java.util.List;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public class SexSelectionAdapter extends BaseQuickAdapter<ScreenLabelBean, BaseViewHolder> {
    private Context mContext;
    List<ScreenLabelBean> mDatas;
    private IClickCallBack call;

    public SexSelectionAdapter(Context context, List<ScreenLabelBean> mDatas) {
        super(R.layout.item_question_selection, mDatas);
        this.mContext = context;
        this.mDatas = mDatas;

    }

    public void setCallBack(IClickCallBack call) {
        this.call = call;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScreenLabelBean item) {

        TextView tvQuestionsLabel = helper.getView(R.id.tv_question_selection_name);
        tvQuestionsLabel.setText(item.getRealmName());
        if (item.isCheck()) {
            tvQuestionsLabel.setTextColor(ContextCompat.getColor(mContext, R.color.color_C58F47));
        } else {
            tvQuestionsLabel.setTextColor(ContextCompat.getColor(mContext, R.color.color_B7B7B7));
        }

        for (int i = 0; i < mDatas.size(); i++) {
            final int tempi = i;
            tvQuestionsLabel.setOnClickListener(v -> {
                for (ScreenLabelBean aa : mDatas) {
                    aa.setCheck(false);
                }
                mDatas.get(tempi).setCheck(false);
                item.setCheck(true);
                notifyDataSetChanged();
                call.callBack(item.getId(), item.getRealmName());
            });
        }
    }

}
