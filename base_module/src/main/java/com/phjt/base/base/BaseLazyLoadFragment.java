package com.phjt.base.base;

import com.phjt.base.mvp.IPresenter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 13:59
 * description:
 * 在多Fragment模式中 需要进行 Fragment数据懒加载可继承此类，并实现 @{@link BaseLazyLoadFragment#lazyLoadData()}
 * 来实现Fragment懒加载
 */
public abstract class BaseLazyLoadFragment<P extends IPresenter> extends BaseFragment<P> {
  /**
   * 界面是否已加载完成
   */
  private boolean isViewCreated;
  /**
   * 是否对用户可见
   */
  private boolean isVisibleToUser;
  /**
   * 数据是否已请求
   */
  private boolean isDataLoaded;

  /**
   * 子类实现具体的数据请求逻辑
   */
  public abstract void lazyLoadData();

  /**
   * ViewPager场景下，判断父Fragment是父可见
   */
  private boolean isParentVisible() {
    Fragment fragment = getParentFragment();
    return fragment == null || (fragment instanceof BaseLazyLoadFragment
        && ((BaseLazyLoadFragment) fragment).isVisibleToUser);
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    this.isVisibleToUser = isVisibleToUser;
    tryLoadData();
  }

  @Override public void onResume() {
    super.onResume();
    isViewCreated = true;
    tryLoadData();
  }

  /**
   * Fragment ---> Fragments
   * ViewPager场景下，当前fragment可见时，如果其子fragment也可见，则让子fragment请求数据
   */
  private void dispatchParentVisibleState() {
    FragmentManager childFragmentManager = getChildFragmentManager();
    List<Fragment> fragments = childFragmentManager.getFragments();
    if (fragments.isEmpty()) {
      return;
    }
    for (Fragment child : fragments) {
      if (child instanceof BaseLazyLoadFragment && ((BaseLazyLoadFragment) child).isVisibleToUser) {
        ((BaseLazyLoadFragment) child).tryLoadData();
      }
    }
  }

  private void tryLoadData() {
    if (isViewCreated && isVisibleToUser && isParentVisible() && !isDataLoaded) {
      lazyLoadData();
      isDataLoaded = true;
      //通知子Fragment请求数据
      dispatchParentVisibleState();
    }
  }
}
