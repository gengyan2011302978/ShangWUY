package com.phjt.shangxueyuan.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.phsxy.utils.LogUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: gengyan
 * date:    2020/8/18 9:09
 * company: 普华集团
 * description: 描述
 */
public class MyFilter implements InputFilter {

    private int mMaxLength;

    public MyFilter(int maxLength) {
        this.mMaxLength = maxLength;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        //dest 已经输入的字符
        String strDest = dest.toString();
        //source 代表新输入待验证过滤的字符串
        String strSource = source.toString();

        if (!TextUtils.isEmpty(strDest) && strDest.length() >= mMaxLength) {
            //返回    "":表示全部过滤，不能输入任何字符      null:表示不做过滤和限制
            return "";
        } else {
            Pattern p = Pattern.compile("[a-zA-Z|\u4e00-\u9fa5]+");
            Matcher m = p.matcher(strSource);
            if (!m.matches()) {
                return "";
            } else {
                String s = strDest + strSource;
                if (s.length() <= mMaxLength) {
                    return null;
                } else {
                    int keep = mMaxLength - strDest.length();
                    return strSource.subSequence(start, keep);
                }
            }
        }
    }

}
