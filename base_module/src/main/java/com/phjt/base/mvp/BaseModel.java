package com.phjt.base.mvp;


import com.phjt.base.integration.IRepositoryManager;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description:
 */
public class BaseModel implements IModel {

  protected IRepositoryManager mRepositoryManager;//用于管理网络请求层, 以及数据缓存层

  public BaseModel(IRepositoryManager mRepositoryManager) {
    this.mRepositoryManager = mRepositoryManager;
  }

  /**
   * 在框架中 {@link BasePresenter#onDestroy()} 时会默认调用 {@link IModel#onDestroy()}
   */
  @Override public void onDestroy() {
    mRepositoryManager = null;
  }
}
