package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.StudyContract;
import com.phjt.shangxueyuan.mvp.model.StudyModel;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
@Module
public abstract class StudyModule {

    @Binds
    abstract StudyContract.Model bindStudyModel(StudyModel model);
}