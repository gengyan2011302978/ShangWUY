package com.phjt.base.base.delegate;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.cache.Cache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description:
 */
public interface IFragment {
  /**
   * 这种方式是以接口形式注入 AppComponent 对象
   * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
   */
  void setupFragmentComponent(@NonNull AppComponent appComponent);

  /**
   * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
   * 此缓存容器和 {@link Fragment} 的生命周期绑定, 如果 {@link Fragment} 在屏幕旋转或者配置更改的情况下
   * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 <a href="https://github.com/JessYanCoding/LifecycleModel">LifecycleModel</a>
   *
   * @return like {@link LruCache}
   */
  @NonNull
  Cache<String, Object> provideCache();

  /**
   * 初始化布局
   */
  View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState);

  /**
   * 初始化数据 一些需要的View
   */
  void initData(@Nullable Bundle savedInstanceState);


  /**
   * 是否使用 EventBus
   * @return
   */
  boolean useEventBus();

  /**
   * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
   * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
   * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
   * <p>
   * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
   * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
   * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
   * <p>
   * Example usage:
   * <pre>
   * public void setData(@Nullable Object data) {
   *     if (data != null && data instanceof Message) {
   *         switch (((Message) data).what) {
   *             case 0:
   *                 loadData(((Message) data).arg1);
   *                 break;
   *             case 1:
   *                 refreshUI();
   *                 break;
   *             default:
   *                 //do something
   *                 break;
   *         }
   *     }
   * }
   *
   * // call setData(Object):
   * Message data = new Message();
   * data.what = 0;
   * data.arg1 = 1;
   * fragment.setData(data);
   * </pre>
   *
   * @param data 当不需要参数时 {@code data} 可以为 {@code null}
   */
  void setData(@Nullable Object data);
}
