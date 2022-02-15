package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyCollectionModule;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyCollectionActivity;


@ActivityScope
@Component(modules = MyCollectionModule.class, dependencies = AppComponent.class)
public interface MyCollectionComponent {
    void inject(MyCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCollectionComponent.Builder view(MyCollectionContract.View view);

        MyCollectionComponent.Builder appComponent(AppComponent appComponent);

        MyCollectionComponent build();
    }
}