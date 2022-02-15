package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.DisabuseBean;

/**
 * @author: Roy
 * date:   2021/6/25
 * company: 普华集团
 * description:
 */
public class ScreenPopAdapterAdapter extends BaseQuickAdapter<DisabuseBean, BaseViewHolder> {

    private Context mContext;
    public ScreenPopAdapterAdapter(Context context) {
        super(R.layout.item_screen_pop);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DisabuseBean item) {
        TextView tvContent = helper.getView(R.id.tv_content_item);
        tvContent.setText(item.getName());
        if (item.isCheck) {
            setTextViewCheck(tvContent);
        } else {
            setTextViewNormal(tvContent);
        }

    }


    public void setTextViewNormal(TextView tvContent) {
        tvContent.setTextColor(mContext.getResources().getColor(R.color.color_FF333333));
    }

    public void setTextViewCheck(TextView tvContent) {
        tvContent.setTextColor(mContext.getResources().getColor(R.color.color_FFFFB30C));

    }
}
