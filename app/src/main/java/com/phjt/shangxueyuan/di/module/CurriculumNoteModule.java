package com.phjt.shangxueyuan.di.module;

import dagger.Binds;
import dagger.Module;

import com.phjt.shangxueyuan.mvp.contract.CurriculumNoteContract;
import com.phjt.shangxueyuan.mvp.model.CurriculumNoteModel;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

@Module
public abstract class CurriculumNoteModule {

    @Binds
    abstract CurriculumNoteContract.Model bindMyNoteModel(CurriculumNoteModel model);
}