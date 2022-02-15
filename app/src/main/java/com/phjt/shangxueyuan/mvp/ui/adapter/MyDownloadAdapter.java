package com.phjt.shangxueyuan.mvp.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author: gengyan
 * date:    2019/11/1 16:30
 * company: 普华集团
 * description: 首页的adapter
 */
public class MyDownloadAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;

    public MyDownloadAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }
}
