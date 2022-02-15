package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author: gengyan
 * date:    2020/5/6 14:10
 * company: 普华集团
 * description: 描述
 */
public class CourseCategoryAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList;
    private ArrayList<String> mTitles;

    public CourseCategoryAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.mFragmentList = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }
}
