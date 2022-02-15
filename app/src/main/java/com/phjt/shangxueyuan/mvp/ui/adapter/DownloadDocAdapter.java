package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.okdownload.core.Util;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.FileLevelBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

import static com.phjt.shangxueyuan.mvp.ui.activity.MyDowloadActivity.isChange;
import static com.phjt.shangxueyuan.mvp.ui.fragment.DocDownloadFragment.mDeleteDocBeanList;


/**
 * @author: renzhiming
 * date:    2020/03/27
 * company: 普华集团
 * description: 消息
 */
public class DownloadDocAdapter extends BaseQuickAdapter<FileLevelBean, BaseViewHolder> {
    public DownloadDocAdapter(Context context, @Nullable List<FileLevelBean> data) {
        super(R.layout.download_doc_adapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileLevelBean item) {
        RoundedImageView ivIcon = helper.getView(R.id.ri_icon);
        AppImageLoader.loadResUrl(item.getActivityUrl(), ivIcon);
        helper.setText(R.id.tv_title,item.getActivityName());
        helper.setText(R.id.tv_number,item.getActivityTotal()+"个文档");
        helper.setText(R.id.tv_downloading,"正在下载："+item.getActivityStatus());
        helper.setText(R.id.tv_size, Util.humanReadableBytes(item.getActivityTotalSize(), true).toString()+"");
        helper.setText(R.id.tv_desc,item.getActivityDesc());
        CheckBox cb_click = helper.getView(R.id.cb_click);
        cb_click.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mDeleteDocBeanList.add(item);
                }else {
                    mDeleteDocBeanList.remove(item);
                }
            }
        });
        if (mDeleteDocBeanList.contains(item)){
            cb_click.setChecked(true);
        }else {
            cb_click.setChecked(false);
        }
        if (isChange){
            cb_click.setVisibility(View.VISIBLE);
        }else {
            cb_click.setVisibility(View.GONE);
        }
    }
}
