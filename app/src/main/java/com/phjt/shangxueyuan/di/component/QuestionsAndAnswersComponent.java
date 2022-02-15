package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.QuestionsAndAnswersModule;
import com.phjt.shangxueyuan.mvp.contract.QuestionsAndAnswersContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.QuestionsAndAnswersActivity;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 10:56
 * @description :
 */
@ActivityScope
@Component(modules = QuestionsAndAnswersModule.class, dependencies = AppComponent.class)
public interface QuestionsAndAnswersComponent {

    void inject(QuestionsAndAnswersActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        QuestionsAndAnswersComponent.Builder view(QuestionsAndAnswersContract.View view);

        QuestionsAndAnswersComponent.Builder appComponent(AppComponent appComponent);

        QuestionsAndAnswersComponent build();
    }
}