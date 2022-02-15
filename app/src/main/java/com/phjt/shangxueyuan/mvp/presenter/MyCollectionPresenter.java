package com.phjt.shangxueyuan.mvp.presenter;


import android.content.Context;
import android.widget.Toast;

import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phsxy.utils.ToastUtils;

import static com.phjt.shangxueyuan.utils.TipsUtil.showTips;


@ActivityScope
public class MyCollectionPresenter extends BasePresenter<MyCollectionContract.Model, MyCollectionContract.View> {

    @Inject
    AppManager mAppManager;

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MyCollectionPresenter(MyCollectionContract.Model model, MyCollectionContract.View rootView) {
        super(model, rootView);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }

}
