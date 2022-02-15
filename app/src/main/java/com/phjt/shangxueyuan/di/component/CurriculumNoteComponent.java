package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CurriculumNoteModule;
import com.phjt.shangxueyuan.mvp.contract.CurriculumNoteContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.CurriculumNoteFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

@FragmentScope
@Component(modules = CurriculumNoteModule.class, dependencies = AppComponent.class)
public interface CurriculumNoteComponent {
    void inject(CurriculumNoteFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CurriculumNoteComponent.Builder view(CurriculumNoteContract.View view);

        CurriculumNoteComponent.Builder appComponent(AppComponent appComponent);

        CurriculumNoteComponent build();
    }
}