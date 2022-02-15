package com.phjt.base.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description: 用于代理{@link Application} 的生命周期
 */
public interface AppLifecycles {

  void attachBaseContext(@NonNull Context base);

  void onCreate(@NonNull Application application);

  void onTerminate(@NonNull Application application);
}
