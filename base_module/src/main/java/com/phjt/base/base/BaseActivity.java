package com.phjt.base.base;

import android.app.Activity;
import android.os.Bundle;


import com.phjt.base.base.delegate.IActivity;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.CacheType;
import com.phjt.base.integration.lifecycle.ActivityLifecycleable;
import com.phjt.base.mvp.IPresenter;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.base.utils.MPermissionUtil;
import com.trello.rxlifecycle3.android.ActivityEvent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * author: Marven
 * date: 2018/7/17
 * dec: activity 基类
 **/
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity
        implements IActivity, ActivityLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * BehaviorSubject --> RxLifeCycle 进行生命周期管理
     */
    protected BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;

    private Unbinder mUnbinder;

    /**
     * 如果当前页面逻辑简单, Presenter 可以为 null
     */
    @Nullable
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArchitectUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = initView(savedInstanceState);
        //如果initLayout返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
        if (layoutResID != 0) {
            setContentView(layoutResID);
            mUnbinder = ButterKnife.bind(this);
        }
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //ButterKnife 在此强制解绑
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;

        //进行资源释放
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mPresenter = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 这个 {@link Activity} 是否会使用 {@link Fragment}, 框架会根据这个属性判断是否注册 {@link
     * FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 {@code false}, 那意味着这个 {@link Activity} 不需要绑定 {@link Fragment}, 那你再在这个 {@link Activity}
     * 中绑定继承于 {@link BaseFragment} 的 {@link Fragment} 将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link Fragment}
     */

    @Override
    public boolean useFragment() {
        return true;
    }


    /**
     * 这个 {@link Activity} 是否会使用 EventBus
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }
}
