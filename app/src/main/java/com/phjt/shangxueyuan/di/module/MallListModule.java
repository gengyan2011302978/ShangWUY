package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.MallListContract;
import com.phjt.shangxueyuan.mvp.model.MallListModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description :
 */
@Module
//构建MallListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class MallListModule {

    @Binds
    abstract MallListContract.Model bindMallListModel(MallListModel model);
}