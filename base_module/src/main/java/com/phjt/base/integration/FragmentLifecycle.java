package com.phjt.base.integration;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.phjt.base.base.delegate.FragmentDelegate;
import com.phjt.base.base.delegate.FragmentDelegateImpl;
import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.IntelligentCache;
import com.phjt.base.utils.Preconditions;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 10:37
 * description:
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
  @Inject
  public FragmentLifecycle() {
  }

  @Override public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull Context context) {

    if (f instanceof IFragment) {
      FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);

      if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
        Cache<String, Object> cache = getCacheFromFragment((IFragment) f);
        fragmentDelegate = new FragmentDelegateImpl(fm, f);
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 Fragment 使用的是 IntelligentCache (框架默认使用)
        cache.put(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE),
            fragmentDelegate);
      }
      fragmentDelegate.onAttach(context);
    }
  }

  @Override public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
      @Nullable Bundle savedInstanceState) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onCreate(savedInstanceState);
    }
  }

  @Override public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull View v, @Nullable Bundle savedInstanceState) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onCreateView(v, savedInstanceState);
    }
  }

  @Override public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f,
      @Nullable Bundle savedInstanceState) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onActivityCreate(savedInstanceState);
    }
  }

  @Override public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onStart();
    }
  }

  @Override public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onResume();
    }
  }

  @Override public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onPause();
    }
  }

  @Override public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onStop();
    }
  }

  @Override
  public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f,
      @NonNull Bundle outState) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onSaveInstanceState(outState);
    }
  }

  @Override
  public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onDestroyView();
    }
  }

  @Override
  public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onDestroy();
    }
  }

  @Override public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
    FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
    if (fragmentDelegate != null) {
      fragmentDelegate.onDetach();
    }
  }

  private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
    if (fragment instanceof IFragment) {
      Cache<String, Object> cache = getCacheFromFragment((IFragment) fragment);
      return (FragmentDelegate) cache.get(
          IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE));
    }
    return null;
  }

  @NonNull
  private Cache<String, Object> getCacheFromFragment(IFragment fragment) {
    Cache<String, Object> cache = fragment.provideCache();
    Preconditions.checkNotNull(cache, "%s cannot be null on Fragment", Cache.class.getName());
    return cache;
  }
}
