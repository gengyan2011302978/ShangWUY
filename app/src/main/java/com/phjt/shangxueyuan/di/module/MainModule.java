package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MainContract;
import com.phjt.shangxueyuan.mvp.model.MainModel;


/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */

@Module
public abstract class MainModule {
    /**
     * Dagger关联 MVP ---> M (model) Instance
     *
     * @param model {@link MainModel}
     * @return {@link MainContract.Model}
     */
    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);
}