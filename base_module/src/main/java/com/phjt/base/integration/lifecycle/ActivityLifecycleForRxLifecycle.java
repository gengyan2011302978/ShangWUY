package com.phjt.base.integration.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.trello.rxlifecycle3.android.ActivityEvent;

import javax.inject.Inject;

import androidx.fragment.app.FragmentActivity;
import dagger.Lazy;
import io.reactivex.subjects.Subject;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 11:04
 * description:
 */
public class ActivityLifecycleForRxLifecycle implements Application.ActivityLifecycleCallbacks {
  @Inject
  Lazy<FragmentLifecycleForRxLifecycle> mFragmentLifecycle;

  @Inject
  public ActivityLifecycleForRxLifecycle() {
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.CREATE);
      if (activity instanceof FragmentActivity) {
        ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);
      }
    }
  }

  @Override public void onActivityStarted(Activity activity) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.START);
    }
  }

  @Override public void onActivityResumed(Activity activity) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.RESUME);
    }
  }

  @Override public void onActivityPaused(Activity activity) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.PAUSE);
    }
  }

  @Override public void onActivityStopped(Activity activity) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.STOP);
    }
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

  }

  @Override public void onActivityDestroyed(Activity activity) {
    if (activity instanceof ActivityLifecycleable) {
      obtainSubject(activity).onNext(ActivityEvent.DESTROY);
    }
  }

  /**
   * 从 {@link BaseActivity} 中获得桥梁对象 {@code BehaviorSubject<ActivityEvent> mLifecycleSubject}
   *
   * @param activity ActivityLifecycleCallbacks 回调时 activity
   * @return BehaviorSubject<ActivityEvent> 实例
   */
  private Subject<ActivityEvent> obtainSubject(Activity activity) {
    return ((ActivityLifecycleable) activity).provideLifecycleSubject();
  }
}
