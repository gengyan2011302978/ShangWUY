package com.phjt.shangxueyuan.common;

import android.content.Context;
import androidx.annotation.NonNull;

import com.phjt.base.di.component.AppComponent;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.utils.ArchitectUtils;

/**
 * @author : austenYang
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date :  2019/10/24 16:26
 */
public class CommonHttpManager {

    private static AppComponent mAppComponent;


    private static class StaticSingletonHolder {
        private static final CommonHttpManager INSTANCE = new CommonHttpManager();
    }

    private CommonHttpManager() {
    }

    public static CommonHttpManager getInstance(Context context) {
        mAppComponent = ArchitectUtils.obtainAppComponentFromContext(context);
        return StaticSingletonHolder.INSTANCE;
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    @NonNull
    public <T> T obtainRetrofitService(@NonNull Class<T> service) {
        return mAppComponent.repositoryManager().obtainRetrofitService(service);
    }

    public static RxErrorHandler getHttpErrorHandler() {
        if (mAppComponent == null){
            throw new NullPointerException("CommonHttpManager --> mAppComponent is null !!!");
        }

        return mAppComponent.rxErrorHandler();
    }


}
