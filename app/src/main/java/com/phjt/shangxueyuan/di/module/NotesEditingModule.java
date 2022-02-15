package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.NotesEditingContract;
import com.phjt.shangxueyuan.mvp.model.NotesEditingModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */

@Module
public abstract class NotesEditingModule {

    @Binds
    abstract NotesEditingContract.Model bindNotesEditingModel(NotesEditingModel model);
}