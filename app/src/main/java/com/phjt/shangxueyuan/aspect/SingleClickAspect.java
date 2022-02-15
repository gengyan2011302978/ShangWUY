package com.phjt.shangxueyuan.aspect;

import android.view.View;


import com.phjt.shangxueyuan.R;
import com.phsxy.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;


/**
 * @author: gengyan
 * date:    2019/11/22
 * company: 普华集团
 * description: 通过点View对象记录上次点击的时间 所以方法参数必须是 View 对象
 */
@Aspect
public class SingleClickAspect {

    /**
     * SingleClick自定义注解的方法
     */
    @Pointcut("execution(@com.phjt.shangxueyuan.annotate.SingleClick * *(..))")
    public void methodAnnotated() {
    }

    /**
     * OnItemClickListener
     */
    @Pointcut("execution(* com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener.*( .. ))")
    public void itemClickAnnotated() {
    }

    private static long clickGapTime = 0;

    public static final int CLICK_GAP_RESPONSE = 1000;

    /**
     * 判断是否应该执行
     *
     * @return true执行，false不执行
     */
    public boolean clickGapFilter() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - clickGapTime < CLICK_GAP_RESPONSE) {
            return false;
        }
        clickGapTime = currentTimeMillis;
        return true;
    }

    /**
     * 要处理的切点连接方法
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("methodAnnotated()|| itemClickAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        if (clickGapFilter()) {
            joinPoint.proceed();
        }
    }
}
