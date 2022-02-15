package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyExerciseContract;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/11 13:54
 */

@ActivityScope
public class MyExerciseModel extends BaseModel implements MyExerciseContract.Model {


    @Inject
    public MyExerciseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}