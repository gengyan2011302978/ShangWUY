package com.phjt.base.base.delegate;

import android.app.Activity;
import android.os.Bundle;


import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:19
 * description:
 */
public class ActivityDelegateImpl implements ActivityDelegate {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity.useEventBus()) {
            EventBusManager.getInstance().register(iActivity);
        }

        //这里提供 AppComponent 对象给 BaseActivity 的子类, 用于 Dagger2 的依赖注入
        iActivity.setupActivityComponent(ArchitectUtils.obtainAppComponentFromContext(mActivity));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity != null && iActivity.useEventBus()) {
            EventBusManager.getInstance().unregister(mActivity);
        }
        this.iActivity = null;
        this.mActivity = null;
    }
}
