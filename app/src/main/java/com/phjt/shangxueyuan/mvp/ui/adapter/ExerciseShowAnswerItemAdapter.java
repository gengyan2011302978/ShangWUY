package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseItemChoseBean;

import java.util.List;

public class ExerciseShowAnswerItemAdapter extends BaseQuickAdapter<ExerciseItemChoseBean, BaseViewHolder> {

    private Context mContext;
    public ExerciseShowAnswerItemAdapter(Context context, @Nullable List<ExerciseItemChoseBean> data) {
        super(R.layout.item_exercise_show_item_chose,data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExerciseItemChoseBean item) {
        TextView tvChoseItem = helper.getView(R.id.tv_chose_item);
        tvChoseItem.setText(item.getOptionName()+"."+item.getOptionContent());
    }
}
