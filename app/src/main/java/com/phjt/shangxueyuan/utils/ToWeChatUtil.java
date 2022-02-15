package com.phjt.shangxueyuan.utils;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;



/**
 * @author: gengyan
 * date:    2020/4/2 22:18
 * company: 普华集团
 * description: 前往微信页面
 */
public class ToWeChatUtil {

    /**
     * 跳转到微信
     */
    public static void goWeChat(Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            TipsUtil.showTips("您还未安装微信");
        }
    }

    /**
     * 赋值到剪切板
     */
    public static void copyContent(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));
    }
}
