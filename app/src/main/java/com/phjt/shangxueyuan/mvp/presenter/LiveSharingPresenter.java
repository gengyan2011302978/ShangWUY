package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveShareImgBean;
import com.phjt.shangxueyuan.mvp.contract.LiveSharingContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/07 17:27
 */

@ActivityScope
public class LiveSharingPresenter extends BasePresenter<LiveSharingContract.Model, LiveSharingContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public LiveSharingPresenter(LiveSharingContract.Model model, LiveSharingContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取分享图片
     */
//    public void getSharepicture(String id) {
//        mModel.getSharepicture(id)
//                .compose(RxUtils.applySchedulers(mRootView))
//                .subscribe(new ErrorHandleSubscriber<BaseBean<List<LiveShareImgBean>>>(mErrorHandler) {
//                    @Override
//                    public void onNext(BaseBean<List<LiveShareImgBean>> integerBaseBean) {
//                        if (integerBaseBean.isOk()) {
//                            mRootView.getgetSharepictureSuccess(integerBaseBean.data);
//                        } else {
//                            mRootView.fail();
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mRootView.fail();
//                    }
//                });
//    }
    public void addUserIntegralRecord(String id) {
        mModel.addUserIntegralRecord(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean integerBaseBean) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.fail();
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
