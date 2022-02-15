package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import com.phjt.shangxueyuan.R;

import butterknife.BindView;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2020/8/26 16:04
 * company: 普华集团
 * description: 课程下载 直播回放弹框
 */
public class DownloadLivePop extends BaseDownloadPop {

    @BindView(R.id.rv_download_live)
    RecyclerView mRvDownloadLive;

    public DownloadLivePop(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_course_download_live;
    }

    @Override
    public RecyclerView getRvView() {
        return mRvDownloadLive;
    }

    @Override
    public int getPopHeight() {
         return AutoSizeUtils.dp2px(mContext, 292f);
    }

    @Override
    public boolean isLivePlayback() {
        return true;
    }
}
