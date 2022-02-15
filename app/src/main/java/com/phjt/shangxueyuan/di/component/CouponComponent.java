package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.CouponModule;
import com.phjt.shangxueyuan.mvp.contract.CouponContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.CouponActivity;


/**
 * @author: Created by GengYan
 * date: 11/24/2020 11:40
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
@Component(modules = CouponModule.class, dependencies = AppComponent.class)
public interface CouponComponent {
    void inject(CouponActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CouponComponent.Builder view(CouponContract.View view);

        CouponComponent.Builder appComponent(AppComponent appComponent);

        CouponComponent build();
    }
}