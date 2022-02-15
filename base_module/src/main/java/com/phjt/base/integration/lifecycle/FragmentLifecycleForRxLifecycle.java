package com.phjt.base.integration.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle3.android.FragmentEvent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.reactivex.subjects.Subject;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 11:06
 * description:
 */
public class FragmentLifecycleForRxLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
  @Inject
  public FragmentLifecycleForRxLifecycle() {
  }

  @Override public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull Context context) {
    super.onFragmentAttached(fm, f, context);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.ATTACH);
    }
  }

  @Override public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
      @Nullable Bundle savedInstanceState) {
    super.onFragmentCreated(fm, f, savedInstanceState);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.CREATE);
    }
  }

  @Override public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull View v, @Nullable Bundle savedInstanceState) {
    super.onFragmentViewCreated(fm, f, v, savedInstanceState);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.CREATE_VIEW);
    }
  }

  @Override public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentStarted(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.START);
    }
  }

  @Override public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentResumed(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.RESUME);
    }
  }

  @Override public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentPaused(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.PAUSE);
    }
  }

  @Override public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentStopped(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.STOP);
    }
  }

  @Override
  public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.DESTROY_VIEW);
    }
  }

  @Override public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentDestroyed(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.DESTROY);
    }
  }

  @Override public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
    super.onFragmentDetached(fm, f);
    if (f instanceof FragmentLifecycleable) {
      obtainSubject(f).onNext(FragmentEvent.DETACH);
    }
  }

  /**
   * 从 {@link BaseFragment} 中获得桥梁对象 {@code BehaviorSubject<ActivityEvent> mLifecycleSubject}
   *
   * @param fragment ActivityLifecycleCallbacks 回调时 activity
   * @return BehaviorSubject<ActivityEvent> 实例
   */
  private Subject<FragmentEvent> obtainSubject(Fragment fragment) {
    return ((FragmentLifecycleable) fragment).provideLifecycleSubject();
  }
}
