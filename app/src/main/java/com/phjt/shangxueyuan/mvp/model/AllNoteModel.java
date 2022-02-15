package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.mvp.contract.AllNoteContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */

@FragmentScope
public class AllNoteModel extends BaseModel implements AllNoteContract.Model {


    @Inject
    public AllNoteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
    /**
     * 获取笔记列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(String courseId, int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCouNotesList(courseId,"",0,pageNo, pageSize,"");
    }
}