package com.phjt.shangxueyuan.mvp.presenter;


import android.content.Context;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionsContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */

@FragmentScope
public class MyCollectionsPresenter extends BasePresenter<MyCollectionsContract.Model, MyCollectionsContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MyCollectionsPresenter(MyCollectionsContract.Model model, MyCollectionsContract.View rootView) {
        super(model, rootView);
    }
    /**
     * 获取课程收藏列表
     */
    public void getCollectionList(int type,int pageNo, int pageSize,boolean isRefresh) {
        mModel.getCollectionList( type,pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyCollectionBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyCollectionBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data,isRefresh);
                        }else {
                            mRootView.loadDataFailure(isRefresh);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFailure(isRefresh);
                    }
                });
    }

    /**
     * 获取专题收藏记录列表
     */
    public void getSpecialFavouriteList(int pageNo, int pageSize,boolean isRefresh) {
        mModel.getSpecialFavouriteList(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyCollectionBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyCollectionBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data,isRefresh);
                        }else {
                            mRootView.loadDataFailure(isRefresh);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFailure(isRefresh);
                    }
                });
    }

    /**
     * 收藏编辑
     */
    public void getFavoriteEdit(Context context, String ids,int mType) {
        mModel.getFavoriteEdit(ids,mType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            showTips("删除成功");
                            mRootView.loadFavoriteEditSuccess();
                        }else {
                            showTips(bean.msg);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        showTips(e.getMessage());
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
