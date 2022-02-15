package com.phjt.shangxueyuan.utils;

import android.content.Context;
import android.text.TextUtils;

import com.phjt.shangxueyuan.R;
import com.phjt.sharestatistic.entity.SharePlatType;
import com.phjt.sharestatistic.inter.ISharePlatResultListener;

/**
 * @author: gengyan
 * date:    2021/6/4 14:53
 * company: 普华集团
 * description: 自定义分享回调
 */
public class ShareResultListener implements ISharePlatResultListener {

    private Context mContext;

    public ShareResultListener(Context context) {
        this.mContext = context;
    }

    @Override
    public void onStart(SharePlatType plat) {

    }

    @Override
    public void onResult(SharePlatType plat) {
        TipsUtil.showTips(mContext.getResources().getString(R.string.str_app_share_success));
    }

    @Override
    public void onError(SharePlatType plat, Throwable throwable) {
        String throwableMessage = throwable.getMessage();
        if (!TextUtils.isEmpty(throwableMessage)) {
            String msg = throwableMessage.substring(throwableMessage.lastIndexOf("：") + 1);
            TipsUtil.showTips(msg);
        } else {
            TipsUtil.showTips("分享出现未知问题");
        }
    }

    @Override
    public void onCancel(SharePlatType plat) {
        TipsUtil.showTips(mContext.getResources().getString(R.string.str_app_share_cancel));
    }
}
