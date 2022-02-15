package com.phjt.base.base;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/4/3 18:39
 * description: 公用的 FragmentPagerAdapter 也可基础 进行功能扩展
 */
public class AdapterViewPager extends FragmentStatePagerAdapter {
  private List<Fragment> mList;
  private CharSequence[] mTitles;

  public AdapterViewPager(FragmentManager fragmentManager, List<Fragment> list) {
    super(fragmentManager);
    this.mList = list;
  }

  public AdapterViewPager(FragmentManager fragmentManager, List<Fragment> list,
      CharSequence[] titles) {
    super(fragmentManager);
    this.mList = list;
    this.mTitles = titles;
  }

  @Override public Fragment getItem(int position) {
    return mList.get(position);
  }

  @Nullable @Override public CharSequence getPageTitle(int position) {
    if (mTitles != null && position < mTitles.length) {
      return mTitles[position];
    }
    return super.getPageTitle(position);
  }

  @Override public int getCount() {
    return mList.size();
  }
}
