package com.phjt.base.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phjt.base.integration.ActivityLifecycle;
import com.phjt.base.integration.AppManager;
import com.phjt.base.integration.FragmentLifecycle;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.integration.RepositoryManager;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.CacheType;
import com.phjt.base.integration.lifecycle.ActivityLifecycleForRxLifecycle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/22 17:33
 * description:
 */
@Module
public abstract class AppModule {

  @Singleton
  @Provides
  static Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
    GsonBuilder builder = new GsonBuilder();
    if (configuration != null) {
      configuration.configGson(application, builder);
    }
    return builder.create();
  }


  @Binds
  abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

  @Binds
  @Named("ActivityLifecycle")
  abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(
      ActivityLifecycle activityLifecycle);

  @Binds
  @Named("ActivityLifecycleForRxLifecycle")
  abstract Application.ActivityLifecycleCallbacks bindActivityLifecycleForRxLifecycle(
      ActivityLifecycleForRxLifecycle activityLifecycleForRxLifecycle);

  @Binds
  abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(
      FragmentLifecycle fragmentLifecycle);

  @Singleton
  @Provides
  static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycles() {
    return new ArrayList<>();
  }

  /**
   * ?????? {@link AppManager} ?????? Dagger ????????????, ???????????? {@link AppComponent#appManager()} ??????
   * ??????????????? AppManager ??????????????????, ?????????????????????????????? {@link AppManager#getAppManager()} ??????, ????????????
   * ?????????????????????????????? {@link AppComponent#appManager()} ?????? {@link AppManager} ?????????, ????????????????????????????????????
   */
  @Singleton
  @Provides
  static AppManager provideAppManager(Application application) {
    return AppManager.getAppManager().init(application);
  }

  @Singleton
  @Provides
  static Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
    return cacheFactory.build(CacheType.EXTRAS);
  }

  public interface GsonConfiguration {
    void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
  }
}


