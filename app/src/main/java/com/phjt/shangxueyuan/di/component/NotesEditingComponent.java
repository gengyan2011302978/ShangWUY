package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.NotesEditingModule;
import com.phjt.shangxueyuan.mvp.contract.NotesEditingContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */

@FragmentScope
@Component(modules = NotesEditingModule.class, dependencies = AppComponent.class)
public interface NotesEditingComponent {
    void inject(NotesEditingFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NotesEditingComponent.Builder view(NotesEditingContract.View view);

        NotesEditingComponent.Builder appComponent(AppComponent appComponent);

        NotesEditingComponent build();
    }
}