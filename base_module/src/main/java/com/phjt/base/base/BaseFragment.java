package com.phjt.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.integration.cache.Cache;
import com.phjt.base.integration.cache.CacheType;
import com.phjt.base.integration.lifecycle.FragmentLifecycleable;
import com.phjt.base.mvp.IPresenter;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.base.utils.MPermissionUtil;
import com.trello.rxlifecycle3.android.FragmentEvent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

/**
 * @author : austen
 * company    : JGT
 * date       : 2019/2/25 11:35
 * description:
 */
public abstract class BaseFragment<P extends IPresenter> extends Fragment
        implements IFragment, FragmentLifecycleable {
    protected final String TAG = this.getClass().getSimpleName();
    protected BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    protected Context mContext;
    @Nullable
    @Inject
    protected P mPresenter;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArchitectUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(
                    CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /**
     * 这个 {@link Fragment} 是否会使用 EventBus
     *
     * @return
     */

    @Override
    public boolean useEventBus() {
        return true;
    }
}
