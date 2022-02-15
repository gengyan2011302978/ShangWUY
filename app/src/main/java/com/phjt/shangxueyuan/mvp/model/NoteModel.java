package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.NoteContract;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 10:53
 */

@FragmentScope
public class NoteModel extends BaseModel implements NoteContract.Model {


    @Inject
    public NoteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}