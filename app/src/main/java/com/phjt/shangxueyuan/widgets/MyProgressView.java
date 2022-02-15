package com.phjt.shangxueyuan.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.phjt.shangxueyuan.R;

import androidx.annotation.Nullable;

/**
 * @author: gengyan
 * date:    2020/10/28 16:41
 * company: 普华集团
 * description: 自定义进度条
 */
public class MyProgressView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;

    private float mHeight;
    private float mWidth;
    /**
     * 进度
     */
    private int mMaxProgress = 100;
    private int mProgress = 100;
    /**
     * 颜色
     */
    private int mProgressBgColor = R.color.white;
    private int mProgressColor = R.color.white;

    public MyProgressView(Context context) {
        super(context);
        mContext = context;
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取当前view的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float progressWidth = mWidth * mProgress / mMaxProgress;
        //防止进度过小 过大导致小圆显示不全
        if (progressWidth < mHeight / 2) {
            progressWidth = mHeight / 2;
        } else if (progressWidth + mHeight / 2 > mWidth) {
            progressWidth = mWidth - mHeight / 2;
        }
        //progress背景
        mPaint.setColor(ContextCompat.getColor(mContext, mProgressBgColor));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rProgressCount = new RectF(0, mHeight / 4, mWidth, 3 * mHeight / 4);
        canvas.drawRoundRect(rProgressCount, 15, 15, mPaint);
        //progress进度
        mPaint.setColor(ContextCompat.getColor(mContext, mProgressColor));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rProgress = new RectF(0, mHeight / 4, progressWidth, 3 * mHeight / 4);
        canvas.drawRoundRect(rProgress, 15, 15, mPaint);
        //progress 大圆
        mPaint.setColor(ContextCompat.getColor(mContext, mProgressBgColor));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rCircleBig = new RectF(progressWidth - mHeight / 2, 0, progressWidth + mHeight / 2, mHeight);
        canvas.drawArc(rCircleBig, 0, 360, false, mPaint);
        //progress 小圆
        mPaint.setColor(ContextCompat.getColor(mContext, mProgressColor));
        mPaint.setStyle(Paint.Style.FILL);
        RectF rCircleSmall = new RectF(progressWidth - mHeight / 4, mHeight / 4, progressWidth + mHeight / 4, 3 * mHeight / 4);
        canvas.drawArc(rCircleSmall, 0, 360, false, mPaint);
    }

    /**
     * 设置进度
     */
    public void setProgress(int maxProgress, int progress, int progressBgColor, int progressColor) {
        this.mMaxProgress = maxProgress;
        this.mProgress = progress;
        this.mProgressBgColor = progressBgColor;
        this.mProgressColor = progressColor;
        postInvalidate();
    }
}
