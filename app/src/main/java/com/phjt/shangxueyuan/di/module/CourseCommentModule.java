package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.CourseCommentContract;
import com.phjt.shangxueyuan.mvp.model.CourseCommentModel;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 11:32
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseCommentModule {

    @Binds
    abstract CourseCommentContract.Model bindCourseCommentModel(CourseCommentModel model);
}