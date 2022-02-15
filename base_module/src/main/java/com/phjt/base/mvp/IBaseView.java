package com.phjt.base.mvp;

import android.app.Activity;
import android.content.Intent;

import com.phjt.base.utils.ArchitectUtils;
import com.phjt.base.utils.Preconditions;

import androidx.annotation.NonNull;

/**
 * author: Marven
 * date: 2018/7/17
 * dec: view基类
 **/
public interface IBaseView {
  /**
   * 显示网络加载框
   */
  default void showLoading() {

  }

  /**
   * 隐藏网络加载框
   */
  default void hideLoading() {

  }

  /**
   * 跳转 {@link Activity}
   *
   * @param intent {@code intent} 不能为 {@code null}
   */
  default void launchActivity(@NonNull Intent intent) {
    Preconditions.checkNotNull(intent);
    ArchitectUtils.startActivity(intent);
  }

  /**
   * 显示信息
   *
   * @param message 消息内容, 不能为 {@code null}
   */
  void showMessage(@NonNull String message);
}
