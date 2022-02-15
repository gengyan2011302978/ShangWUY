package com.phjt.shangxueyuan.mvp.model;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;


@ActivityScope
public class MyCollectionModel extends BaseModel implements MyCollectionContract.Model {


    @Inject
    public MyCollectionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



}