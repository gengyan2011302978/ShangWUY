package com.phjt.shangxueyuan.di.module;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.CourseIntroduceContract;
import com.phjt.shangxueyuan.mvp.model.CourseDetailIntroduceModel;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class CourseDetailIntroduceModule {

    @Binds
    abstract CourseIntroduceContract.Model bindCourseDetailIntroduceModel(CourseDetailIntroduceModel model);
}