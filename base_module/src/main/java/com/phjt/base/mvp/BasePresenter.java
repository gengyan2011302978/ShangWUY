package com.phjt.base.mvp;

import android.app.Activity;


import com.phjt.base.utils.Preconditions;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * author: Marven
 * date: 2018/7/20
 * dec:代理对象的基础实现 ：  一些基础的方法
 *
 * @param <V> 视图接口对象(view) 具体业务各自继承自IBaseView
 * @param <M> 业务请求返回的具体对象
 **/
public class BasePresenter<M extends IModel, V extends IBaseView> implements IPresenter {
  protected CompositeDisposable mCompositeDisposable;
  protected M mModel;
  protected V mRootView;

  public BasePresenter(M model,V rootView) {

    Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
    Preconditions.checkNotNull(rootView, "%s cannot be null", IBaseView.class.getName());
    this.mModel = model;
    this.mRootView = rootView;
  }

  /**
   * 在框架中  {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
   */
  @Override public void onDestroy() {
    //解除订阅
    unDispose();
    if (mModel != null) {
      mModel.onDestroy();
    }
    this.mModel = null;
    this.mRootView = null;
    this.mCompositeDisposable = null;
  }

  /**
   * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
   * 可在 {@link Activity#onDestroy()} 中使用 {@link #unDispose()} 停止正在执行的 RxJava 任务,避免内存泄漏
   */
  public void addDispose(Disposable disposable) {
    if (mCompositeDisposable == null) {
      mCompositeDisposable = new CompositeDisposable();
    }
    //将所有 Disposable 放入容器集中处理
    mCompositeDisposable.add(disposable);
  }

  private void unDispose() {
    //保证 Activity 结束时取消所有正在执行的订阅
    if (mCompositeDisposable != null) {
      mCompositeDisposable.clear();
    }
  }
}
