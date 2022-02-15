package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2020/6/9 9:48
 * company: 普华集团
 * description: 带进度条的文本
 */
public class ProgressTextView extends AppCompatTextView {

    private int mBorderWidth = AutoSizeUtils.dp2px(getContext(), 1);
    private int mBorderWidthColor = Color.parseColor("#2371FF");
    private int mCorners = AutoSizeUtils.dp2px(getContext(), 1.5f);
    /**
     * 边框 进度 背景
     */
    private Paint mCornerPaint, mProgressPaint, mBgPaint, mTextPaint;
    private int mProgress = 0;
    private String mString = "下载";

    private int[] colors = new int[]{0xff2871FF, 0xff0076FF};

    private RectF rectF;


    public ProgressTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs) {
//        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressTextView);
//        mProgress = a.getInteger(R.styleable.ProgressTextView_progress, 0);
//        a.recycle();

        mCornerPaint = new Paint();
        mProgressPaint = new Paint();
        mBgPaint = new Paint();
        mTextPaint = new Paint();

        mCornerPaint.setAntiAlias(true);
        mCornerPaint.setDither(true);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);

        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(AutoSizeUtils.dp2px(getContext(), 12));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        doDrawProgress(canvas);
    }

    /**
     * 画进度条
     *
     * @param canvas
     */
    private void doDrawProgress(Canvas canvas) {
        rectF = new RectF(mBorderWidth / 2f, mBorderWidth / 2f,
                getMeasuredWidth() - mBorderWidth, getMeasuredHeight() - mBorderWidth);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseline = rectF.centerY() + distance;

        canvas.save();

        if (TextUtils.equals("下载", mString) || TextUtils.equals("下载中", mString)|| TextUtils.equals("无网络", mString)|| TextUtils.equals("已暂停", mString)) {
            //未下载
            mCornerPaint.setStrokeWidth(mBorderWidth);
            mCornerPaint.setStyle(Paint.Style.STROKE);
            mCornerPaint.setColor(mBorderWidthColor);
            canvas.drawRoundRect(rectF, AutoSizeUtils.dp2px(getContext(), mCorners), AutoSizeUtils.dp2px(getContext(), mCorners), mCornerPaint);

            mTextPaint.setColor(Color.parseColor("#1174FF"));
            canvas.drawText(mString, rectF.centerX(), baseline, mTextPaint);

        } else if (TextUtils.equals("已下载", mString)) {
            //已下载
            mBgPaint.setStyle(Paint.Style.FILL);
            mBgPaint.setColor(Color.parseColor("#A8C6FF"));
            canvas.drawRoundRect(rectF, AutoSizeUtils.dp2px(getContext(), mCorners), AutoSizeUtils.dp2px(getContext(), mCorners), mBgPaint);

            mTextPaint.setColor(Color.parseColor("#FFFFFF"));
            canvas.drawText(mString, rectF.centerX(), baseline, mTextPaint);
        }

        canvas.restore();
    }


    /**
     * 设置进度
     *
     * @param progress 0-100
     */
    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setmTextPaint(String string) {
        mString = string;
        invalidate();
    }

    public void setProgress(int soFarBytes, int totalBytes) {
        mProgress = (int) ((soFarBytes / (float) totalBytes) * 100);
        invalidate();
    }

}
