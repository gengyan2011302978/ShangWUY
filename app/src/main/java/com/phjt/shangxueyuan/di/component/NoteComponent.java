package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.NoteModule;
import com.phjt.shangxueyuan.mvp.contract.NoteContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.NoteFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 10:53
 */

@FragmentScope
@Component(modules = NoteModule.class, dependencies = AppComponent.class)
public interface NoteComponent {
    void inject(NoteFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NoteComponent.Builder view(NoteContract.View view);

        NoteComponent.Builder appComponent(AppComponent appComponent);

        NoteComponent build();
    }
}