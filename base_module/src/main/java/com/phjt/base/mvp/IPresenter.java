package com.phjt.base.mvp;

import android.app.Activity;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description:
 * @see BasePresenter
 */
public interface IPresenter {

  /**
   * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
   */
  void onDestroy();
}
