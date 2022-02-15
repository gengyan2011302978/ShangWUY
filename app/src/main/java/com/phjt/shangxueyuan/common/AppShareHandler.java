package com.phjt.shangxueyuan.common;

import android.content.Context;
import android.text.TextUtils;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.sharestatistic.entity.SharePlatType;
import com.phjt.sharestatistic.inter.ISharePlatResultListener;


/**
 * @author: gengyan
 * date:    2020/3/24 
 * company: 普华集团
 * description: 描述
 */
public class AppShareHandler implements ISharePlatResultListener {

    private Context mContext;

    public AppShareHandler(Context context) {
        mContext = context;
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
