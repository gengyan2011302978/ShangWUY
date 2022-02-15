package com.phjt.shangxueyuan.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.module.MessageModule;
import com.phjt.shangxueyuan.mvp.contract.MessageContract;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.shangxueyuan.mvp.ui.activity.MessageActivity;

/**
 * @author: Created by Template
 * date: 2020/03/30 13:46
 * company: 普华集团
 * description: 描述
 */

@ActivityScope
@Component(modules = MessageModule.class, dependencies = AppComponent.class)
public interface MessageComponent {
    /**
     * 指定Dagger要注入的目标类
     *
     * @param activity Dagger's target class
     */
    void inject(MessageActivity activity);

    @Component.Builder
    interface Builder {
        /**
         * 传入View层实现类
         *
         * @param view MVP ---> V 层
         * @return Component.Builder
         */
        @BindsInstance
        MessageComponent.Builder view(MessageContract.View view);

        /**
         * 传入 {@link AppComponent}
         *
         * @param appComponent {@link AppComponent}
         * @return Component.Builder
         */
        MessageComponent.Builder appComponent(AppComponent appComponent);

        /**
         * 构建者
         *
         * @return Component
         */
        MessageComponent build();
    }
}