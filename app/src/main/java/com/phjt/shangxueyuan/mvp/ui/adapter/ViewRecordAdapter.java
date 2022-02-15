package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ViewRecordBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/27 11:39
 * company: 普华集团
 * description: 试听课程的adapter
 */
public class ViewRecordAdapter extends BaseQuickAdapter<ViewRecordBean.RecordsBean, BaseViewHolder> {

    private Context mContext;

    public ViewRecordAdapter(Context context, @Nullable List<ViewRecordBean.RecordsBean> data) {
        super(R.layout.item_view_record, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ViewRecordBean.RecordsBean item) {
        GlideUtils.load(item.getCoverImg(), helper.getView(R.id.iv_audition_item), R.drawable.image_placeholder);
        helper.setVisible(R.id.tv_has_been_removed, item.getUpState() == 0)
                .setVisible(R.id.gray_view, item.getUpState() == 0)
                .setText(R.id.tv_audition_title_item, item.getName())
                .setText(R.id.tv_audition_content_item, item.getCouDescribe())
                .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getStudyNum()) + "人在学")
                .setText(R.id.tv_recent_watch_time, "最后观看时间： " + item.getUpdateTime());
    }
}
