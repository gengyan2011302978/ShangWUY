package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.view.roundView.RoundTextView;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class StudyScreenPopAdapter extends BaseQuickAdapter<ScreenlBean, BaseViewHolder> {

    private Context mContext;
    private RecyclerView rvScreen;
    //是否是单选
    private boolean isSingle;

    public StudyScreenPopAdapter(Context context, @Nullable List<ScreenlBean> data, RecyclerView rvScreen) {
        super(R.layout.item_study_pop_screen, data);
        this.mContext = context;
        this.rvScreen = rvScreen;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScreenlBean item) {
        RoundTextView tvContent = helper.getView(R.id.tv_content_item);
        tvContent.setText(item.getRealmName());

        if (item.isCheck) {
            setTextViewCheck(tvContent);
        } else {
            setTextViewNormal(tvContent);
        }

        tvContent.setOnClickListener(v -> {
            if (isSingle) {
                resetRv();
                item.setCheck(true);
                setTextViewCheck(tvContent);
            } else {
                if (item.isCheck) {
                    item.setCheck(false);
                    setTextViewNormal(tvContent);
                } else {
                    item.setCheck(true);
                    setTextViewCheck(tvContent);
                }
            }
            EventBusManager.getInstance().post(new EventBean(EventBean.SCREENING_STATUS, "1"));
        });
    }

    public void resetRv() {
        for (int i = 0; i < rvScreen.getChildCount(); i++) {
            ConstraintLayout layout = (ConstraintLayout) rvScreen.getChildAt(i);
            RoundTextView tvContent = layout.findViewById(R.id.tv_content_item);
            setTextViewNormal(tvContent);

            getData().get(i).setCheck(false);
        }
    }

    public void setTextViewNormal(RoundTextView tvContent) {
        tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF5F605E));
        tvContent.getDelegate().setStrokeColor(ContextCompat.getColor(mContext, R.color.color_FFD2D2D2));
        tvContent.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FFF8F8F8));
    }

    public void setTextViewCheck(RoundTextView tvContent) {
        tvContent.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFC8E1A));
        tvContent.getDelegate().setStrokeColor(ContextCompat.getColor(mContext, R.color.color_FFFC8E1A));
        tvContent.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FFFFF0D8));
    }
}
