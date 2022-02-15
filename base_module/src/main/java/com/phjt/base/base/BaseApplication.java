package com.phjt.base.base;

import android.app.Application;
import android.content.Context;


import com.phjt.base.base.delegate.AppDelegate;
import com.phjt.base.base.delegate.AppLifecycles;
import com.phjt.base.di.component.AppComponent;

import androidx.annotation.NonNull;

/**
 * @author    : austen
 * company    : JGT
 * date       : 2019/3/25 11:35
 * description:
 *
 * 目的：对于{@link BaseApplication} 、{@link BaseActivity} 、{@link BaseActivity} 减少其封装。
 * 原因：1.当基类中的逻辑封装过多难管理
 * 2.由于Java是单继承，比如一旦遇到某个第三方库其要求必须继承它的Activity,但你有需要使用 BaseActivity 类
 * 封装的功能特性，这是由于你不能修改第三方库的 Activity,你只能让你的 BaseActivity 类继承它。 那么其他大量
 * 的 Activity 并不需要这个第三库的特性或功能，让大量 Actvity 被迫继承它，这是得不偿失的。
 * 3.对框架的封装完成后，肯定不希望使用者可以去修改基类，而且当用依赖方式引入后也是无法对其进行修改的。更不希望
 * 使用者再去继承已经封装好 {@link BaseActivity} 对其再进行扩展。
 * 分析：考我们为什么一定要封装 BaseActivity 通过继承来实现一些 Activity 的公共逻辑,而不能将公共逻辑封装到其他类里面?
 * ---> 因为我们必须利用 Actitvity 的生命周期方法，在对应的生命周期中执行响应的公共逻辑。
 * 解决：生命周期 ---> 通过代理类将系统调用的重要生命周期回调代理出去，在不修改基础架构的代码的情况下扩展其功能。
 * {@link BaseApplication}  、{@link BaseFragment} 和 {@link BaseActivity} 原理一样，通过代理达到可配置性。
 */
public class BaseApplication extends Application implements App {
  private AppLifecycles mAppDelegate;

  @Override protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    if (mAppDelegate == null) {
      this.mAppDelegate = new AppDelegate(base);
    }

    this.mAppDelegate.attachBaseContext(base);
  }

  @Override public void onCreate() {
    super.onCreate();
    if (mAppDelegate != null) {
      mAppDelegate.onCreate(this);
    }
  }

  @Override public void onTerminate() {
    super.onTerminate();
    if (mAppDelegate != null) {
      mAppDelegate.onTerminate(this);
    }
  }

  /**
   * 返回 AppComponent 实例，可以通过 AppComponent 获取 AppComponent 提供的实例
   * @return AppComponent 通过 AppDelegate 类实例化
   */
  @NonNull @Override public AppComponent getAppComponent() {
    return ((App) mAppDelegate).getAppComponent();
  }
}
