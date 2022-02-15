package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.DocDownloadContract;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class DocDownloadPresenter extends BasePresenter<DocDownloadContract.Model, DocDownloadContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public DocDownloadPresenter(DocDownloadContract.Model model, DocDownloadContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
