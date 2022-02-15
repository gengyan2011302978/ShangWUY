package com.phjt.base.integration;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/25 13:53
 * description:
 */
public interface IRepositoryManager {
  /**
   * 根据传入的 Class 获取对应的 Retrofit service
   *
   * @param service Retrofit service class
   * @param <T>     Retrofit service 类型
   * @return Retrofit service
   */
  @NonNull
  <T> T obtainRetrofitService(@NonNull Class<T> service);



  /**
   * 获取 {@link Context}
   *
   * @return {@link Context}
   */
  @NonNull
  Context getContext();
}
