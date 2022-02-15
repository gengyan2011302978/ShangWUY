package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.event.ChangeInfoEvent;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.mvp.ui.activity.DocDownloadListActivity;
import com.phjt.shangxueyuan.widgets.ProgressTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.phjt.shangxueyuan.mvp.ui.activity.DocDownloadListActivity.deleteBeanList;

/**
 * @author: renzhiming
 * date:    2020/03/27
 * company: 普华集团
 * description: 消息
 */
public class DocDownloadAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    public DocDownloadAdapter(Context context, @Nullable List<File> data) {
        super(R.layout.item_data, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        helper.addOnClickListener(R.id.pb_tv_item);
        helper.addOnClickListener(R.id.tv_name_item);
        helper.setText(R.id.tv_name_item,item.getFileName());
        helper.setText(R.id.iv_pdf_item,item.getFileType());
        ProgressTextView pTv = helper.getView(R.id.pb_tv_item);
        CheckBox cb_click = helper.getView(R.id.cb_click);
        cb_click.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    deleteBeanList.add(item);
                }else {
                    deleteBeanList.remove(item);
                }
                EventBus.getDefault().post(new ChangeInfoEvent(2));
            }
        });
        if (deleteBeanList.contains(item)){
            cb_click.setChecked(true);
        }else {
            cb_click.setChecked(false);
        }
        if (DocDownloadListActivity.isChange){
            cb_click.setVisibility(View.VISIBLE);
        }else {
            cb_click.setVisibility(View.GONE);
        }
        final File file=item;
        switch (file.getStatus()) {
            case File.DOWNLOAD_PAUSE://暂停->开始
                pTv.setmTextPaint("已暂停");
                pTv.setProgress(file.getProgress());
                break;
            case File.DOWNLOAD_PROCEED://开始->暂停
                pTv.setmTextPaint("下载中");
//                pTv.setProgress(file.getProgress());
                break;
            case File.DOWNLOAD_ERROR://出错
//                pTv.setProgress(file.getProgress());
                pTv.setmTextPaint("无网络");
                break;
            case File.DOWNLOAD_COMPLETE://完成
                pTv.setmTextPaint("已下载");
                break;
            case File.DOWNLOAD_REDYA://准备下载 ->开始
                pTv.setProgress(file.getProgress());
                break;
            default:
                break;
        }
    }
}
