package com.phjt.shangxueyuan.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:EditText输入类型
 */
public class LimitInputTextWatcher implements TextWatcher {
    /**
     * 对EditText输入规则进行校验，规则可以自己定义
     */


    private EditText et = null;

    /**
     * 筛选条件
     */

    private String regex;

    /**
     * 默认的筛选条件(正则:只能输入中文)
     */

    private String DEFAULT_REGEX2 = "[^\u4E00-\u9FA5]";

    /**
     * 默认的筛选条件(正则:只能输入中文和字母)
     */

    private String DEFAULT_REGEX = "[^A-Z|a-z|\u4e00-\u9fa5]";

    /**
     * 构造方法
     *
     * @param et
     */

    public LimitInputTextWatcher(EditText et,int type) {
        this.et = et;
        if ( type==0) {
            //只能输入中文和字母
            this.regex = DEFAULT_REGEX;
        }else {
            //只能输入中文
            this.regex = DEFAULT_REGEX2;
        }

    }

    /**
     * 构造方法
     *
     * @param et
     * @param regex 筛选条件
     */

    public LimitInputTextWatcher(EditText et, String regex) {

        this.et = et;

        this.regex = regex;

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        String str = editable.toString();

        String inputStr = clearLimitStr(regex, str);

        et.removeTextChangedListener(this);

// et.setText方法可能会引起键盘变化,所以用editable.replace来显示内容

        editable.replace(0, editable.length(), inputStr.trim());

        et.addTextChangedListener(this);

    }

    /**
     * 清除不符合条件的内容
     *
     * @param regex
     * @return
     */

    private String clearLimitStr(String regex, String str) {

        return str.replaceAll(regex, "");

    }
}

