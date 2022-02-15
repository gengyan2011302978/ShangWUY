package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.NotesReviewDetailContract;
import com.phjt.shangxueyuan.mvp.model.NotesReviewDetailModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/18 13:44
 */

@Module
public abstract class NotesReviewDetailModule {

    @Binds
    abstract NotesReviewDetailContract.Model bindNotesReviewDetailModel(NotesReviewDetailModel model);
}