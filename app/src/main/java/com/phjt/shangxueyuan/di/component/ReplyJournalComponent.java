package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.ReplyJournalModule;
import com.phjt.shangxueyuan.mvp.contract.ReplyJournalContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.ReplyJournalFragment;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/16 14:46
 */

@FragmentScope
@Component(modules = ReplyJournalModule.class, dependencies = AppComponent.class)
public interface ReplyJournalComponent {
    void inject(ReplyJournalFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ReplyJournalComponent.Builder view(ReplyJournalContract.View view);

        ReplyJournalComponent.Builder appComponent(AppComponent appComponent);

        ReplyJournalComponent build();
    }
}