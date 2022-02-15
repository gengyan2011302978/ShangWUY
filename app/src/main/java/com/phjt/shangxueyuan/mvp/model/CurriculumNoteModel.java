package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.mvp.contract.CurriculumNoteContract;
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
public class CurriculumNoteModel extends BaseModel implements CurriculumNoteContract.Model {


    @Inject
    public CurriculumNoteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取笔记列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(String courseId,String pointId,int pageNo, int pageSize,int type,String courseType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getCouNotesList(courseId,pointId,type,pageNo, pageSize,courseType);
    }

    /**
     * 笔记点赞、取消点赞
     */
    @Override
    public Observable<BaseBean> thumbsUp(int notesId, int courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).thumbsUp(notesId, courseId);
    }

}