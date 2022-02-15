package com.phjt.shangxueyuan.utils;

import android.text.TextUtils;

/**
 * @author: gengyan
 * date:    2021/3/23 15:00
 * company: 普华集团
 * description: 描述
 */
public class StringUtil {

    /**
     * 截取字符串，去掉最后一位
     */
    public static String subStrEnd(String str) {
        if (!TextUtils.isEmpty(str)) {
            return str.substring(0, str.length() - 1);
        }
        return null;
    }
}
