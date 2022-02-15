package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.mvp.contract.DocDownloadContract;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:46
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class DocDownloadModel extends BaseModel implements DocDownloadContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public DocDownloadModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}