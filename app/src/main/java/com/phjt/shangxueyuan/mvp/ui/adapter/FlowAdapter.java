package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.view.roundView.RoundTextView;

/**
 * @author: Roy
 * date:   2021/7/2
 * company: 普华集团
 * description:
 */
public class FlowAdapter extends BaseQuickAdapter<TutorAnsweringQuestionsBean.RealmList, BaseViewHolder> {

    private Context mContext;

    public FlowAdapter(Context context) {
        super(R.layout.item_realm);
        this.mContext = context;

    }


    @Override
    protected void convert(BaseViewHolder helper, TutorAnsweringQuestionsBean.RealmList item) {
        RoundTextView tvContent = helper.getView(R.id.tv_content_item);
        tvContent.setText(item.getRealmName());

    }

}
