package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.TeacherLiveListContract;
import com.phjt.shangxueyuan.mvp.model.TeacherLiveListModel;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class TeacherLiveListModule {

    @Binds
    abstract TeacherLiveListContract.Model bindTeacherLiveListModel(TeacherLiveListModel model);
}