package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * @author: Roy
 * date:    2020/3/30
 * company: 普华集团
 * description: 公共的dialog
 */
public class BaseDialog extends Dialog {
    private int res;

    public BaseDialog(Context context, int theme, int res) {
        super(context, theme);
        // 自动生成的构造函数存根
        this.res = res;
        setContentView(res);
        setCanceledOnTouchOutside(false);
    }
}