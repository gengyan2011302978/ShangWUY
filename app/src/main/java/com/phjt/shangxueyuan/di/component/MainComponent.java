package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MainModule;
import com.phjt.shangxueyuan.mvp.contract.MainContract;

import com.phjt.base.di.scope.FragmentScope;
import com.phjt.shangxueyuan.mvp.ui.fragment.MainFragment;

/**
 * @author: Created by Template
 * date: 2020/03/27 13:48
 * company: 普华集团
 * description: 描述
 */

@FragmentScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    /**
     * 指定Dagger要注入的目标类
     *
     * @param $fragment Dagger's target class
     */
    void inject(MainFragment fragment);

    @Component.Builder
    interface Builder {
        /**
         * 传入View层实现类
         *
         * @param view MVP ---> V 层
         * @return Component.Builder
         */
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        /**
         * 传入 {@link AppComponent}
         *
         * @param appComponent {@link AppComponent}
         * @return Component.Builder
         */
        MainComponent.Builder appComponent(AppComponent appComponent);

        /**
         * 构建者
         *
         * @return Component
         */
        MainComponent build();
    }
}