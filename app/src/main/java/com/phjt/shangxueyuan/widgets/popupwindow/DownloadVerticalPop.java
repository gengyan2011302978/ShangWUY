package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.VideoDownloadListActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/6/5 16:54
 * company: 普华集团
 * description: 课程下载 竖直方向弹框
 */
public class DownloadVerticalPop extends BaseDownloadPop {

    @BindView(R.id.rv_download)
    RecyclerView mRvDownload;

    public DownloadVerticalPop(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_course_download_vertical;
    }

    @Override
    public RecyclerView getRvView() {
        return mRvDownload;
    }

    @Override
    public int getPopHeight() {
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }

    @Override
    public boolean isLivePlayback() {
        return false;
    }

    @OnClick({R.id.iv_close, R.id.tv_download_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_download_list:
                Intent intent = new Intent(mContext, VideoDownloadListActivity.class);
                intent.putExtra("levelName", CourseDetailActivity.level_name);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }
}
