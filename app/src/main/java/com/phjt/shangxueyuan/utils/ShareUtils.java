package com.phjt.shangxueyuan.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.widgets.dialog.ShareToPlatformDialog;
import com.phjt.sharestatistic.ShareApi;
import com.phjt.sharestatistic.entity.SDescription;
import com.phjt.sharestatistic.entity.SImage;
import com.phjt.sharestatistic.entity.ShareImageInfo;
import com.phjt.sharestatistic.entity.SharePlatType;
import com.phjt.sharestatistic.entity.ShareUrlInfo;

/**
 * @author: gengyan
 * date:    2021/2/2 17:21
 * company: 普华集团
 * description: 分享工具类
 */
public class ShareUtils {

    /**
     * 分享弹框
     */
    public static void showSharePop(Activity activity, ShareBean shareBean) {
        ShareToPlatformDialog shareDialog = new ShareToPlatformDialog(activity, (statusId, name) -> shareUrlData(activity, shareBean, statusId));
        shareDialog.setCancelable(true);
        shareDialog.setCanceledOnTouchOutside(true);
        shareDialog.show();
    }

    /**
     * 链接分享
     */
    public static void shareUrlData(Activity activity, ShareBean shareBean, int statusId) {

        SDescription sDescription = new SDescription(shareBean.getTitle(), new SImage(shareBean.getImgUrl()), shareBean.getContent());

        ShareUrlInfo.ShareUrlInfoBuilder urlInfoBuilder = ShareUrlInfo.ShareUrlInfoBuilder.buildShareUrlInfo()
                .with(activity)
                .withDesc(sDescription)
                .shareUrl(shareBean.getUrl())
                .callback(new ShareResultListener(activity));

        ShareApi.getInstance().shareUrl(urlInfoBuilder.platType(statusId == 0 ? SharePlatType.WeChat : SharePlatType.WeChatCircle).build());
    }

    /**
     * 图片分享
     */
    public static void shareImgData(Activity activity, Bitmap bitmap, SharePlatType platType) {
        ShareImageInfo.ShareImageInfoBuilder imgBuilder = ShareImageInfo.ShareImageInfoBuilder
                .buildShareImageInfo()
                .with(activity)
                .callback(new ShareResultListener(activity))
                .thumbImage(new SImage(bitmap))
                .shareImages(new SImage(bitmap));

        ShareApi.getInstance().shareImage(imgBuilder.platType(platType).build());
    }
}
