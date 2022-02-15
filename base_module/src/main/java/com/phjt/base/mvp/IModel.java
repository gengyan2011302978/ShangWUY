package com.phjt.base.mvp;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description:
 */
public interface IModel {

  /**
   * 在框架中  {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
   */
  void onDestroy();
}
