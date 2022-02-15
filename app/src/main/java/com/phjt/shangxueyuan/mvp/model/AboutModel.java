package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.AboutContract;


@ActivityScope
public class AboutModel extends BaseModel implements AboutContract.Model {


    @Inject
    public AboutModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

}