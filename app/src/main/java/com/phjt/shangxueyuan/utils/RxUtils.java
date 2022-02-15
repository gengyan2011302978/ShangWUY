package com.phjt.shangxueyuan.utils;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.utils.RxLifecycleUtils;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : austen
 * company    : 普华
 * date       : 2019/4/3 14:19
 * description: RxJava 工具类
 */
public class RxUtils {

    /**
     * 携带Loading 操作的 网络 ObservableTransformer 封装
     *
     * @param view IBaseView
     * @param <T>  T
     * @return ObservableTransformer RxJava compose
     */
    public static <T> ObservableTransformer<T, T> applySchedulersWithLoading(IBaseView view) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(view::hideLoading)
                .compose(RxLifecycleUtils.bindToLifecycle(view));
    }

    /**
     * 不携带Loading 操作的 网络 ObservableTransformer 封装
     *
     * @param view IBaseView
     * @param <T>  T
     * @return ObservableTransformer RxJava compose
     */
    public static <T> ObservableTransformer<T, T> applySchedulers(IBaseView view) {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(view));
    }

    /**
     * 网络 ObservableTransformer 封装
     *
     * @param <T>  T
     * @return ObservableTransformer RxJava compose
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
