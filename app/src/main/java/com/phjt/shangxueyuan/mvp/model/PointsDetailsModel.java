package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.PointsDetailsContract;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:09
 */

@ActivityScope
public class PointsDetailsModel extends BaseModel implements PointsDetailsContract.Model {


    @Inject
    public PointsDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}