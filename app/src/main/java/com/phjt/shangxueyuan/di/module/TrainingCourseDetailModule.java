package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TrainingCourseDetailContract;
import com.phjt.shangxueyuan.mvp.model.TrainingCourseDetailModel;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 10:30
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TrainingCourseDetailModule {

    @Binds
    abstract TrainingCourseDetailContract.Model bindTrainingCourseDetailModel(TrainingCourseDetailModel model);
}