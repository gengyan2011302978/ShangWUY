package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MyInvitationContract;
import com.phjt.shangxueyuan.mvp.model.MyInvitationModel;


/**
 * @author: Created by GengYan
 * date: 08/28/2020 10:41
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class MyInvitationModule {

    @Binds
    abstract MyInvitationContract.Model bindMyInvitationModel(MyInvitationModel model);
}