package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ViewColumnBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.TimeUtils;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/27 11:39
 * company: 普华集团
 * description: 试听课程的adapter
 */
public class ViewmyColumnAdapter extends BaseQuickAdapter<ViewColumnBean.RecordsBean, BaseViewHolder> {

    private Context mContext;

    public ViewmyColumnAdapter(Context context, @Nullable List<ViewColumnBean.RecordsBean> data) {
        super(R.layout.item_view_column, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewColumnBean.RecordsBean item) {
        GlideUtils.load(item.getCoverImg(), helper.getView(R.id.iv_audition_item), R.drawable.image_placeholder);
        String progress = "";
        if (item.getSumTimeLong() <= 0) {
            progress = "0";
        }else {
            progress = TimeUtils.getStudyLook(item.getCourseWatchTime(), item.getSumTimeLong());
        }
        helper.setText(R.id.tv_audition_title_item, item.getName())
                .setText(R.id.tv_audition_content_item, item.getCouDescribe())
                .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getStudyNum()) + "人在学")
                .setText(R.id.tv_has_been_removed, "已学习" + progress + "%");
    }
}
