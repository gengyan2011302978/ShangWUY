package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.mvp.contract.ScannerContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */

@ActivityScope
public class ScannerModel extends BaseModel implements ScannerContract.Model {


    @Inject
    public ScannerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 扫一扫
     */
    @Override
    public Observable<BaseBean> getScanQRcode(String certificate) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getScanQRcode( certificate);
    }

    /**
     * 扫一扫
     */
    @Override
    public Observable<BaseBean> getScanRcodeConfirm(String certificate,int optionType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getScanRcodeConfirm( certificate, optionType);
    }

}