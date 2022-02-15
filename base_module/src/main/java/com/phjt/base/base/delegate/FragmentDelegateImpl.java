package com.phjt.base.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;


import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/3/18 13:34
 * description:
 */
public class FragmentDelegateImpl implements FragmentDelegate {

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment iFragment;
    private Unbinder mUnbinder;

    /**
     * @param fragmentManager
     * @param fragment
     */
    public FragmentDelegateImpl(@NonNull FragmentManager fragmentManager,
                                @NonNull Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = (IFragment) fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //如果要使用eventbus请将此方法返回true
        if (iFragment.useEventBus()) {
            //注册到事件主线
            EventBusManager.getInstance().register(mFragment);
        }


        iFragment.setupFragmentComponent(
                ArchitectUtils.obtainAppComponentFromContext(mFragment.getActivity()));
    }

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            mUnbinder = ButterKnife.bind(mFragment, view);
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        iFragment.initData(savedInstanceState);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        //ButterKnife 在此强制解绑
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            try {
                mUnbinder.unbind();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        //如果要使用eventbus请将此方法返回true
        if (iFragment != null && iFragment.useEventBus()) {
            //注册到事件主线
            EventBusManager.getInstance().unregister(mFragment);
        }
        this.mUnbinder = null;
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public boolean isAdded() {
        return mFragment != null && mFragment.isAdded();
    }
}
