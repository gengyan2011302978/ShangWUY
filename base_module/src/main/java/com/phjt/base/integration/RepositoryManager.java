package com.phjt.base.integration;

import android.app.Application;
import android.content.Context;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.CacheType;
import com.phjt.base.utils.Preconditions;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/25 13:54
 * description:
 * RepositoryManager 单例对象
 */

@Singleton
public class RepositoryManager implements IRepositoryManager {
  @Inject
  Lazy<Retrofit> mRetrofit;

  @Inject
  Application mApplication;

  @Inject
  Cache.Factory mCachefactory;

  private Cache<String, Object> mRetrofitServiceCache;

  @Inject
  public RepositoryManager() {
  }

  @NonNull @Override public <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
    return createWrapperService(serviceClass);
  }

  private <T> T createWrapperService(Class<T> serviceClass) {
    Preconditions.checkNotNull(serviceClass, "serviceClass == null");

    return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
        new Class<?>[] { serviceClass },
        (proxy, method, args) -> {

          // 此处在调用 serviceClass 中的方法时触

          if (method.getReturnType() == Observable.class) {
            // 如果方法返回值是 Observable 的话，则包一层再返回，
            // 只包一层 defer 由外部去控制耗时方法以及网络请求所处线程，
            // 如此对原项目的影响为 0，且更可控。

            return Observable.defer((Callable<ObservableSource<?>>) () -> {
              final T service = getRetrofitService(serviceClass);

              return ((Observable) getRetrofitMethod(service, method).invoke(service, args));
            });
          } else if (method.getReturnType() == Single.class) {
            // 如果方法返回值是 Single 的话，则包一层再返回。
            return Single.defer(() -> {
              final T service = getRetrofitService(serviceClass);
              // 执行真正的 Retrofit 动态代理的方法
              return ((Single) getRetrofitMethod(service, method)
                  .invoke(service, args));
            });
          }
          // 返回值不是 Observable 或 Single 的话不处理。

          // 返回值不是 Observable 或 Single 的话不处理。
          final T service = getRetrofitService(serviceClass);
          return getRetrofitMethod(service, method).invoke(service, args);
        });
  }

  /**
   * 根据传入的 Class 获取对应的 Retrofit service
   *
   * @param serviceClass ApiService class
   * @param <T> ApiService class
   * @return ApiService
   */
  private <T> T getRetrofitService(Class<T> serviceClass) {
    if (mRetrofitServiceCache == null) {
      mRetrofitServiceCache = mCachefactory.build(CacheType.RETROFIT_SERVICE_CACHE);
    }
    Preconditions.checkNotNull(mRetrofitServiceCache,
        "Cannot return null from a Cache.Factory#build(int) method");
    T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
    if (retrofitService == null) {
      retrofitService = mRetrofit.get().create(serviceClass);
      mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
    }
    return retrofitService;
  }

  private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
    return service.getClass().getMethod(method.getName(), method.getParameterTypes());
  }

  @NonNull @Override public Context getContext() {
    return mApplication;
  }
}
