package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liulishuo.okdownload.core.Util;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.event.ChangeInfoEvent;
import com.phjt.shangxueyuan.bean.model.File;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.phjt.shangxueyuan.mvp.ui.activity.VideoDownloadListActivity.deleteBeanList;
import static com.phjt.shangxueyuan.mvp.ui.activity.VideoDownloadListActivity.isChange;

/**
 * @author: renzhiming
 * date:    2020/03/27
 * company: 普华集团
 * description: 消息
 */
public class VideoDownloadAdapter extends BaseQuickAdapter<File, BaseViewHolder> {
    public VideoDownloadAdapter(Context context, @Nullable List<File> data) {
        super(R.layout.video_download_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, File item) {
        RelativeLayout relat_progress = helper.getView(R.id.relat_progress);
        helper.setText(R.id.tv_title,item.getFileName());
        helper.addOnClickListener(R.id.cb_click);
        helper.addOnClickListener(R.id.relat_item);
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
        if (isChange){
            cb_click.setVisibility(View.VISIBLE);
        }else {
            cb_click.setVisibility(View.GONE);
        }
        final File file=item;
        switch (file.getStatus()) {
            case File.DOWNLOAD_PAUSE://暂停->开始
                relat_progress.setVisibility(View.VISIBLE);
                helper.setProgress(R.id.progressBar,file.getProgress());
                helper.setBackgroundRes(R.id.iv_status,R.drawable.download_arrow);
                helper.setText(R.id.tv_progress,"暂停");
                helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setTextColor(R.id.tv_progress,ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setText(R.id.tv_downloading,Util.humanReadableBytes(Long.parseLong(file.getSizeStr()), true).toString()+"");
                break;
            case File.DOWNLOAD_PROCEED://开始->暂停
                relat_progress.setVisibility(View.VISIBLE);
                helper.setProgress(R.id.progressBar,file.getProgress());
                helper.setBackgroundRes(R.id.iv_status,R.drawable.download_arrow);
                helper.setText(R.id.tv_progress,file.getProgress()+"%");
                helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.color_333333));
                helper.setTextColor(R.id.tv_progress,ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setText(R.id.tv_downloading, Util.humanReadableBytes(Long.parseLong(file.getSizeStr()), true).toString()+"");
                break;
            case File.DOWNLOAD_ERROR://出错
                relat_progress.setVisibility(View.VISIBLE);
                helper.setProgress(R.id.progressBar,file.getProgress());
                helper.setText(R.id.tv_progress,"无网络");
                helper.setTextColor(R.id.tv_progress,ContextCompat.getColor(mContext, R.color.color_FF3E3E));
                helper.setBackgroundRes(R.id.iv_status,R.drawable.download_arrow);
                helper.setText(R.id.tv_downloading,Util.humanReadableBytes(Long.parseLong(file.getSizeStr()), true).toString()+"");
                break;
            case File.DOWNLOAD_COMPLETE://完成
                helper.setProgress(R.id.progressBar,file.getProgress());
                helper.setBackgroundRes(R.id.iv_status,R.drawable.check_true_icon);
                relat_progress.setVisibility(View.GONE);
                helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setTextColor(R.id.tv_progress,ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setText(R.id.tv_downloading,""+Util.humanReadableBytes(Long.parseLong(file.getSizeStr()), true).toString());
                break;
            case File.DOWNLOAD_REDYA://准备下载 ->开始
                relat_progress.setVisibility(View.VISIBLE);
                helper.setProgress(R.id.progressBar,file.getProgress());
                helper.setBackgroundRes(R.id.iv_status,R.drawable.download_arrow);
                helper.setText(R.id.tv_progress,file.getProgress()+"%");
                helper.setText(R.id.tv_downloading,"0M");
                helper.setTextColor(R.id.tv_progress,ContextCompat.getColor(mContext, R.color.color_999999));
                helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.color_333333));
//                helper.setVisible(R.id.tv_progress,false);
                break;
            default:
                break;
        }

    }

}
