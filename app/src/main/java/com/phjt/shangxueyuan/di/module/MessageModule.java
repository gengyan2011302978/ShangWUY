package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.MessageContract;
import com.phjt.shangxueyuan.mvp.model.MessageModel;


/**
 * @author: Created by Template
 * date: 2020/03/30 13:46
 * company: 普华集团
 * description: 描述
 */

@Module
public abstract class MessageModule {
    /**
     * Dagger关联 MVP ---> M (model) Instance
     *
     * @param model {@link MessageModel}
     * @return {@link MessageContract.Model}
     */
    @Binds
    abstract MessageContract.Model bindMessageModel(MessageModel model);
}