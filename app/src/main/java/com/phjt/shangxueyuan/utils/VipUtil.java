package com.phjt.shangxueyuan.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.activity.BasicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phsxy.utils.SPUtils;

/**
 * @author: gengyan
 * date:    2020/4/3 14:27
 * company: 普华集团
 * description: 描述
 */
public class VipUtil {

    /**
     * 前往Vip页面
     */
    public static void toVipPage(Context context) {
        Intent intent = new Intent(context, MyWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_VIP));
        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "超级会员");
        context.startActivity(intent);
    }

    /**
     * Item中 Vip Icon展示
     *
     * @param vipState vip状态 0:普通用户  1:普通VIP  2:永久VIP  3:VIP已过期
     * @param ivVip    图片View
     */
    public static void setVipImage(int vipState, ImageView ivVip) {
        if (vipState == 1 || vipState == 2) {
            ivVip.setVisibility(View.VISIBLE);
            ivVip.setImageResource(R.drawable.ic_vip);
        } else if (vipState == 3) {
            ivVip.setVisibility(View.VISIBLE);
            ivVip.setImageResource(R.drawable.ic_vip_un);
        } else {
            ivVip.setVisibility(View.GONE);
        }
    }

    /**
     * Item 中 头像、昵称及vip 点击事件
     *
     * @param context      上下文
     * @param userId       当前item的userId
     * @param vipState     vip状态 0:普通用户  1:普通VIP  2:永久VIP  3:VIP已过期
     * @param isIvVipClick 是否点击的vip图标
     */
    public static void iconAndVipClick(Context context, String userId, int vipState, boolean isIvVipClick) {
        String spUserId = SPUtils.getInstance().getString(Constant.SP_USER_ID);
        if (TextUtils.equals(userId, spUserId)) {
            if (isIvVipClick) {
                if (vipState == 3) {
                    toVipPage(context);
                } else {
                    Intent intent = new Intent(context, BasicInfoActivity.class);
                    context.startActivity(intent);
                }
            } else {
                Intent intent = new Intent(context, BasicInfoActivity.class);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 点击头像，直接跳转
     */
    public static void iconAndVipClick(Context context, String userId, int vipState) {
        iconAndVipClick(context, userId, vipState, false);
    }
}
