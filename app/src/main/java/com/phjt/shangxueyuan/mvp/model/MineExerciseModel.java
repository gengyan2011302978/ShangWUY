package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.mvp.contract.MineExerciseContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/03/17 10:24
 */

@FragmentScope
public class MineExerciseModel extends BaseModel implements MineExerciseContract.Model {


    @Inject
    public MineExerciseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BaseListBean<MineExerciseBean>>> myExerciseBookList(String commentType,  int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).myExerciseBookList(commentType, currentPage, pageSize);
    }

}