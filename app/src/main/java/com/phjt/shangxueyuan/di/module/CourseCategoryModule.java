package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseCategoryContract;
import com.phjt.shangxueyuan.mvp.model.CourseCategoryModel;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseCategoryModule {

    @Binds
    abstract CourseCategoryContract.Model bindCourseCategoryModel(CourseCategoryModel model);
}