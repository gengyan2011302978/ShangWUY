package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Dialog;
import android.content.Context;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.interf.ICourseDetailDialog;

import androidx.annotation.NonNull;

/**
 * @author: gengyan
 * date:    2020/6/2 10:50
 * company: 普华集团
 * description: 描述
 */
public abstract class BaseCourseDialog extends Dialog {

    protected Context mContext;
    protected ICourseDetailDialog mListener;

    public void setListener(ICourseDetailDialog mListener) {
        this.mListener = mListener;
    }

    public BaseCourseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;

        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }
}
