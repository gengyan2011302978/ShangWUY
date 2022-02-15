package com.phjt.shangxueyuan.di.module;

import com.phjt.shangxueyuan.mvp.contract.ForgetPasswordContract;
import com.phjt.shangxueyuan.mvp.model.ForgetPasswordModel;

import dagger.Binds;
import dagger.Module;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class ForgetPasswordModule {

    @Binds
    abstract ForgetPasswordContract.Model bindForgetPasswordModel(ForgetPasswordModel model);
}