package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.di.module.TutorAnsweringQuestionsModule;
import com.phjt.shangxueyuan.mvp.contract.TutorAnsweringQuestionsContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.TutorAnsweringQuestionsFragment;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
@FragmentScope
@Component(modules = TutorAnsweringQuestionsModule.class, dependencies = AppComponent.class)
public interface TutorAnsweringQuestionsComponent {

    void inject(TutorAnsweringQuestionsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TutorAnsweringQuestionsComponent.Builder view(TutorAnsweringQuestionsContract.View view);

        TutorAnsweringQuestionsComponent.Builder appComponent(AppComponent appComponent);

        TutorAnsweringQuestionsComponent build();
    }
}