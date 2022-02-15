package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseCatalogContract;
import com.phjt.shangxueyuan.mvp.model.CourseCatalogModel;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:52
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseCatalogModule {

    @Binds
    abstract CourseCatalogContract.Model bindCourseCatalogModel(CourseCatalogModel model);
}