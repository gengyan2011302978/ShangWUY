package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.TutorAnsweringQuestionsContract;
import com.phjt.shangxueyuan.mvp.model.TutorAnsweringQuestionsModel;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
@Module
//构建TutorAnsweringQuestionsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class TutorAnsweringQuestionsModule {

    @Binds
    abstract TutorAnsweringQuestionsContract.Model bindTutorAnsweringQuestionsModel(TutorAnsweringQuestionsModel model);
}