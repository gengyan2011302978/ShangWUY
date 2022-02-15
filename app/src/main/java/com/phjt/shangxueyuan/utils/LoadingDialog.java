package com.phjt.shangxueyuan.utils;

import android.content.Context;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.widgets.dialog.BaseDialog;


/**
 * @author: liuyouxi
 * date: 2019/11/26 19:48
 * company: 普华集团
 * description: 描述
 */
public class LoadingDialog {

    private ImageView ivIng;
    private BaseDialog mDialog;

    private static class Holder {
        private static final LoadingDialog INSTANCE = new LoadingDialog();
    }

    public static LoadingDialog getInstance() {
        return Holder.INSTANCE;
    }

    public void show(Context context) {

        mDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_loading);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        ivIng = mDialog.findViewById(R.id.iv_loading);

        //加载动画
        loadIng(ivIng);
    }

    //加载动画
    private void loadIng(ImageView ivIng) {
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation animation_rotate = new RotateAnimation(0, +360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        //第一个参数fromDegrees为动画起始时的旋转角度 //第二个参数toDegrees为动画旋转到的角度
        //第三个参数pivotXType为动画在X轴相对于物件位置类型 //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
        //第五个参数pivotXType为动画在Y轴相对于物件位置类型 //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setStartOffset(0);
        animation_rotate.setDuration(700);
        LinearInterpolator lir = new LinearInterpolator();
        animationSet.setInterpolator(lir);
        animationSet.addAnimation(animation_rotate);
        ivIng.startAnimation(animationSet);
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
            if (null != ivIng) {
                ivIng.clearAnimation();
            }
        }
    }
}

