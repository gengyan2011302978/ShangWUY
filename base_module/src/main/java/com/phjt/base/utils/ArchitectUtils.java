package com.phjt.base.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.phjt.base.base.App;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.AppManager;


/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/22 14:55
 * description: Architecture Utils
 *
 * 项目框架中使用的工具类
 */
public class ArchitectUtils {

  /**
   * 跳转界面 1, 通过 {@link AppManager#startActivity(Class)}
   */
  public static void startActivity(Class activityClass) {
    AppManager.getAppManager().startActivity(activityClass);
  }

  /**
   * 跳转界面 2, 通过 {@link AppManager#startActivity(Intent)}
   */
  public static void startActivity(Intent content) {
    AppManager.getAppManager().startActivity(content);
  }

  /**
   * 跳转界面 3
   */
  public static void startActivity(Activity activity, Class homeActivityClass) {
    Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
    activity.startActivity(intent);
  }

  /**
   * 跳转界面 4
   */
  public static void startActivity(Activity activity, Intent intent) {
    activity.startActivity(intent);
  }

  /**
   *
   *
   * 获取 {@link AppComponent} 实例对象
   * AppComponent 对象是通过 代理类{@link AppDelegate#onCreate(Application)} 方法实例化的，
   * 并且通过接口
   *
   * @param context Context
   * @return AppComponent  实例对象
   */
  public static AppComponent obtainAppComponentFromContext(Context context) {
    Preconditions.checkNotNull(context, "%s cannot be null", Context.class.getName());
    Preconditions.checkState(context.getApplicationContext() instanceof App,
        "%s must be implements %s", context.getApplicationContext().getClass().getName(),
        App.class.getName());

    return ((App) context.getApplicationContext()).getAppComponent();
  }
}
