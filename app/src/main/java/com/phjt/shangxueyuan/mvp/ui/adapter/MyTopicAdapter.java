package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.utils.GlideUtils;

/**
 * @author: Roy
 * date:   2020/10/21
 * company: 普华集团
 * description:
 */
public class MyTopicAdapter extends BaseQuickAdapter<MyTopicBean, BaseViewHolder> {

    private Context mContext;

    public MyTopicAdapter(Context context ) {
        super(R.layout.item_my_topic );
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTopicBean item) {
        GlideUtils.load(item.getCoverImg(), helper.getView(R.id.iv_audition_item), R.drawable.image_placeholder);
        helper.setText(R.id.tv_audition_title_item, "#"+item.getTopicName())
                .setText(R.id.tv_audition_content_item, item.getFocusDescribe());
    }
}
