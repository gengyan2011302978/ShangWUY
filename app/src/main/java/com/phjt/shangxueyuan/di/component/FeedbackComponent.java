package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.FeedbackModule;
import com.phjt.shangxueyuan.mvp.contract.FeedbackContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.FeedbackActivity;


@ActivityScope
@Component(modules = FeedbackModule.class, dependencies = AppComponent.class)
public interface FeedbackComponent {
    void inject(FeedbackActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedbackComponent.Builder view(FeedbackContract.View view);

        FeedbackComponent.Builder appComponent(AppComponent appComponent);

        FeedbackComponent build();
    }
}