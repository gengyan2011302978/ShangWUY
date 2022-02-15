package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyNotesModule;
import com.phjt.shangxueyuan.mvp.contract.MyNotesContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MyNotesActivity;


@ActivityScope
@Component(modules = MyNotesModule.class, dependencies = AppComponent.class)
public interface MyNotesComponent {
    void inject(MyNotesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyNotesComponent.Builder view(MyNotesContract.View view);

        MyNotesComponent.Builder appComponent(AppComponent appComponent);

        MyNotesComponent build();
    }
}