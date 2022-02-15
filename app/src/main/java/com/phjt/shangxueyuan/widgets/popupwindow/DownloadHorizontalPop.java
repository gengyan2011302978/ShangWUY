package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.phjt.shangxueyuan.R;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2020/6/5 17:20
 * company: 普华集团
 * description: 课程下载 水平方向弹框
 */
public class DownloadHorizontalPop extends BaseDownloadPop {

    @BindView(R.id.rv_download)
    RecyclerView mRvDownload;

    public DownloadHorizontalPop(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_course_download_horizontall;
    }

    @Override
    public RecyclerView getRvView() {
        return mRvDownload;
    }

    @Override
    public int getPopHeight() {
        return AutoSizeUtils.dp2px(mContext, 260f);
    }

    @Override
    public boolean isLivePlayback() {
        return false;
    }

    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;
        }
    }

}
