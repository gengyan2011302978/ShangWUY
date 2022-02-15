package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.ReplyJournalContract;
import com.phjt.shangxueyuan.mvp.model.ReplyJournalModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/16 14:46
 */

@Module
public abstract class ReplyJournalModule {

    @Binds
    abstract ReplyJournalContract.Model bindReplyJournalModel(ReplyJournalModel model);
}