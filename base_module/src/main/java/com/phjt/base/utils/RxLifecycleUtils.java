package com.phjt.base.utils;


import com.phjt.base.integration.lifecycle.ActivityLifecycleable;
import com.phjt.base.integration.lifecycle.FragmentLifecycleable;
import com.phjt.base.integration.lifecycle.Lifecycleable;
import com.phjt.base.mvp.IBaseView;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.RxLifecycle;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.RxLifecycleAndroid;

import androidx.annotation.NonNull;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 11:09
 * description: 使用此类操作 RxLifecycle 的特性
 */
public class RxLifecycleUtils {

  private RxLifecycleUtils() {
    throw new IllegalStateException("you can't instantiate me!");
  }

  /**
   * 绑定 Activity 的指定生命周期
   *
   * @param view 生命周期
   * @param event 生命周期
   * @param <T> 生命周期
   * @return 生命周期
   */
  public static <T> LifecycleTransformer<T> bindUnitEvent(@NonNull final IBaseView view, final
  ActivityEvent event) {
    Preconditions.checkNotNull(view, "view == null");
    if (view instanceof ActivityLifecycleable) {
      return bindUntilEvent((ActivityLifecycleable) view, event);
    }
    return null;
  }

  /**
   * 绑定 Fragment 的指定生命周期
   *
   * @param lifecycleable 生命周期
   * @param event 生命周期
   * @param <T> 生命周期
   * @param <R> 生命周期
   * @return 生命周期
   */
  public static <T, R> LifecycleTransformer<T> bindUntilEvent(
          @NonNull final Lifecycleable<R> lifecycleable, final R event) {
    Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
    return RxLifecycle.bindUntilEvent(lifecycleable.provideLifecycleSubject(), event);
  }

  /**
   *
   * @param view
   * @param <T>
   * @return
   */
  public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IBaseView view) {
    Preconditions.checkNotNull(view, "view == null");
    if (view instanceof Lifecycleable) {
      return bindToLifecycle((Lifecycleable) view);
    } else {
      throw new IllegalArgumentException("view isn't Lifecycleable");
    }
  }

  /**
   * 绑定生命周期到 Activity / Fragment上
   *
   * @param lifecycleable 生命周期
   * @param <T> 生命周期
   * @return LifecycleTransformer
   */
  public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull Lifecycleable lifecycleable) {
    Preconditions.checkNotNull(lifecycleable, "lifecycleable == null");
    if (lifecycleable instanceof ActivityLifecycleable) {
      return RxLifecycleAndroid.bindActivity(
          ((ActivityLifecycleable) lifecycleable).provideLifecycleSubject());
    } else if (lifecycleable instanceof FragmentLifecycleable) {
      return RxLifecycleAndroid.bindFragment(
          ((FragmentLifecycleable) lifecycleable).provideLifecycleSubject());
    } else {
      throw new IllegalArgumentException("Lifecycleable not match");
    }
  }
}


