package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author: liuyouxi
 * date: 2019/11/12 15:42
 * company: 普华集团
 * description: 描述
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;

    private List<Fragment> mFragments;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

}
