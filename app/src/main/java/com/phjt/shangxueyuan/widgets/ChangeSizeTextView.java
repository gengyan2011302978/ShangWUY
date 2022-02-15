package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.SPUtils;

/**
 * @author: Roy
 * date:   2021/6/21
 * company: 普华集团
 * description:可变大小的TextView
 */
public class ChangeSizeTextView  extends androidx.appcompat.widget.AppCompatTextView {
    public ChangeSizeTextView(Context context) {
        this(context, null);
    }

    public ChangeSizeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float size = SPUtils.getInstance().getFloat(Constant.SP_SET_TEXT_SIZE, 1.0f);
        post(() -> {
            float s = getTextSize();
            setTextSize(px2sp(getContext(), s * size));
        });
    }

    private int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }
}
