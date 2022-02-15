package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MyInvitationModule;
import com.phjt.shangxueyuan.mvp.contract.MyInvitationContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.MyInvitationFragment;


/**
 * @author: Created by GengYan
 * date: 08/28/2020 10:41
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
@Component(modules = MyInvitationModule.class, dependencies = AppComponent.class)
public interface MyInvitationComponent {
    void inject(MyInvitationFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyInvitationComponent.Builder view(MyInvitationContract.View view);

        MyInvitationComponent.Builder appComponent(AppComponent appComponent);

        MyInvitationComponent build();
    }
}