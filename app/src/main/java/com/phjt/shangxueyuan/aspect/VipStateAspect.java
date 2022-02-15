package com.phjt.shangxueyuan.aspect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.phjt.base.integration.AppManager;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.VipStateBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import io.reactivex.functions.Consumer;

/**
 * @author: gengyan
 * date:    2020/4/3 14:35
 * company: 普华集团
 * description: 描述
 */
@Aspect
public class VipStateAspect {

    @Pointcut("execution(@com.phjt.shangxueyuan.annotate.VipStateCheck * *(..))")
    public void vipStatedAnnotated() {
    }

    @SuppressLint("CheckResult")
    @Around("vipStatedAnnotated()")
    public void checkVipState(ProceedingJoinPoint joinPoint) throws Throwable {
        Context activity = AppManager.getAppManager().getTopActivity();
        CommonHttpManager.getInstance(activity)
                .obtainRetrofitService(ApiService.class)
                .getVipState()
                .compose(RxUtils.applySchedulers())
                .subscribe(new Consumer<BaseBean<VipStateBean>>() {
                    @Override
                    public void accept(BaseBean<VipStateBean> baseBean) throws Exception {
                        if (baseBean.isOk()) {
                            VipStateBean stateBean = baseBean.data;
                            Integer vipState = stateBean.getVipState();
                            //0.普通用户；1.普通vip；2.永久vip；3.vip已过期
                            if (vipState == 1) {
                                authSuccess(joinPoint);
                            } else if (vipState == 2) {
                                authSuccess(joinPoint);
                            } else {
                                Activity currentActivity = AppManager.getAppManager().getCurrentActivity();
                                if (currentActivity != null) {
                                    currentActivity.runOnUiThread(() -> {
                                        DialogUtils.showVipDialog(currentActivity);
                                    });
                                }
                            }
                        } else {
                            TipsUtil.showTips(baseBean.msg);
                        }
                    }
                }, throwable -> {
                    LogUtils.e("===============" + throwable.getMessage());
                    authSuccess(joinPoint);
                });
    }

    public void authSuccess(ProceedingJoinPoint joinPoint) {
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            LogUtils.e("aroundJoinPoint checkUserAuth Throwable ", throwable.getMessage());
            throwable.printStackTrace();
        }
    }
}
