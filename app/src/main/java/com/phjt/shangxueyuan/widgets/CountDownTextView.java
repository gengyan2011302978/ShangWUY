package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.os.CountDownTimer;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.phjt.shangxueyuan.R;

public class CountDownTextView extends androidx.appcompat.widget.AppCompatTextView {
    /**
     * 倒计时结束是否圆角 0：圆角；1不是圆角
     */
    private int isCircle = 0;

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCircle(int isCircles) {
        this.isCircle = isCircles;
    }


    public void startCountDown() {
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTextView.this.setSelected(true);
                CountDownTextView.this.setEnabled(false);
                CountDownTextView.this.setText(String.format("%ds", millisUntilFinished / 1000));
                if (isCircle == 0) {
                    CountDownTextView.this.setBackgroundResource(R.drawable.shape_bg_countdown);
                }
            }

            @Override
            public void onFinish() {
                CountDownTextView.this.setSelected(false);
                CountDownTextView.this.setEnabled(true);
                CountDownTextView.this.setText("获取验证码");
                if (isCircle == 0) {
                    CountDownTextView.this.setBackgroundResource(R.drawable.shape_btn_login);
                }
            }
        }.start();
    }

    /**
     * 弟子群账号计时开始
     */
    public void startCountDownDiscipleGroup(Context context) {
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                CountDownTextView.this.setSelected(true);
                CountDownTextView.this.setEnabled(false);
                CountDownTextView.this.setText(String.format("%ds", millisUntilFinished / 1000));
                CountDownTextView.this.setText(String.format("%ds", millisUntilFinished / 1000));
                CountDownTextView.this.setTextColor(ContextCompat.getColor(context, R.color.color_2871FF));
            }

            @Override
            public void onFinish() {
                CountDownTextView.this.setSelected(false);
                CountDownTextView.this.setEnabled(true);
                CountDownTextView.this.setText("获取验证码");
                CountDownTextView.this.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
            }
        }.start();
    }
}
