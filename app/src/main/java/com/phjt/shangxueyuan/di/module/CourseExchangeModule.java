package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseExchangeContract;
import com.phjt.shangxueyuan.mvp.model.CourseExchangeModel;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:48
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseExchangeModule {

    @Binds
    abstract CourseExchangeContract.Model bindCourseExchangeModel(CourseExchangeModel model);
}