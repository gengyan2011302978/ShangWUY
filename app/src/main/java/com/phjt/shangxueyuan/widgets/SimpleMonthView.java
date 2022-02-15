package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.phjt.shangxueyuan.R;

/**
 * 高仿魅族日历布局
 */

public class SimpleMonthView extends MonthView {
    private Paint mSchemeBasicPaint = new Paint();
    private int mPadding;
    private int mH, mW;
    private int mRadius;
    private float mHookLineLength;
    Path.Direction direction;
    Bitmap dstbmp;

    public SimpleMonthView(Context context) {
        super(context);
        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.STROKE);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setColor(0xff333333);
        mSchemeBasicPaint.setFakeBoldText(true);
        mPadding = dipToPx(getContext(), 4);
        mH = dipToPx(getContext(), 2);
        mW = dipToPx(getContext(), 8);
        dstbmp = imageScale(Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_signed)), 30, 20);
    }

    @Override
    protected void onPreviewHook() {

        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        mSelectedPaint.setStyle(Paint.Style.FILL);
//        canvas.drawArc(cx, cy, 90f, mSelectedPaint);
        canvas.drawRoundRect(x + mPadding, y + mPadding, x + mItemWidth - mPadding, y + mItemHeight - mPadding, 8f, 8f, mSelectedPaint);
        return true;
    }

    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,
                true);
        return dstbmp;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y,boolean isSelected) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        mSchemeBasicPaint.setStyle(Paint.Style.STROKE);
        mSchemeBasicPaint.setColor(Color.WHITE);
        if (calendar.getSchemeColor()==3){
            canvas.drawBitmap(dstbmp, (x + mItemWidth / 2) - 10, mTextBaseLine + y + mItemHeight / 10, mSchemeBasicPaint);
        }else if (calendar.getSchemeColor()==2){
            if (isSelected){
                canvas.drawText("补卡", x + mItemWidth / 2, mTextBaseLine + y + mItemHeight / 10 + 15, mCurMonthLunarTextPaintT);
            }else {
                canvas.drawText("补卡", x + mItemWidth / 2, mTextBaseLine + y + mItemHeight / 10 + 15, mCurMonthLunarTextPaint);
            }
        }else {

        }


//        canvas.drawRect(x + mItemWidth / 2 - mW / 2,
//                mTextBaseLine + y + mItemHeight / 10 + mW / 2,
//                x + mItemWidth / 2 + mW / 2,
//                mTextBaseLine + y + mItemHeight / 10 - mW / 2, mSchemeBasicPaint);

//        canvas.drawRect(x + mItemWidth / 2 - mW / 2,
//                y + mItemHeight - mH * 2 ,
//                x + mItemWidth / 2 + mW / 2,
//                y + mItemHeight - mH , mSchemeBasicPaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY - 20,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY - 20,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && true && true ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY - 20,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && true && false ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
