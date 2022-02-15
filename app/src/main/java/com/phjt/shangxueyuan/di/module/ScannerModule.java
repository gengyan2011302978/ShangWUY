package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ScannerContract;
import com.phjt.shangxueyuan.mvp.model.ScannerModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */

@Module
public abstract class ScannerModule {

    @Binds
    abstract ScannerContract.Model bindScannerModel(ScannerModel model);
}