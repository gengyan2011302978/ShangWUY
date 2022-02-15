package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.NotesReviewDetailModule;
import com.phjt.shangxueyuan.mvp.contract.NotesReviewDetailContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.NotesReviewDetailFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/18 13:44
 */

@FragmentScope
@Component(modules = NotesReviewDetailModule.class, dependencies = AppComponent.class)
public interface NotesReviewDetailComponent {
    void inject(NotesReviewDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        NotesReviewDetailComponent.Builder view(NotesReviewDetailContract.View view);

        NotesReviewDetailComponent.Builder appComponent(AppComponent appComponent);

        NotesReviewDetailComponent build();
    }
}