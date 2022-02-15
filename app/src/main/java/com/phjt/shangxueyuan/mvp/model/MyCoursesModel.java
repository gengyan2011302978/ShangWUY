package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.MyCoursesContract;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:00
 */

@ActivityScope
public class MyCoursesModel extends BaseModel implements MyCoursesContract.Model {


    @Inject
    public MyCoursesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}