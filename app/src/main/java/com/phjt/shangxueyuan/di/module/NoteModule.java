package com.phjt.shangxueyuan.di.module;

import com.phjt.base.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.phjt.shangxueyuan.mvp.contract.NoteContract;
import com.phjt.shangxueyuan.mvp.model.NoteModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 10:53
 */

@Module
public abstract class NoteModule {

    @Binds
    abstract NoteContract.Model bindNoteModel(NoteModel model);
}