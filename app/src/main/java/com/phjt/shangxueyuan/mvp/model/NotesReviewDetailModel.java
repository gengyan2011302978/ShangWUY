package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.mvp.contract.NotesReviewDetailContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/18 13:44
 */

@FragmentScope
public class NotesReviewDetailModel extends BaseModel implements NotesReviewDetailContract.Model {


    @Inject
    public NotesReviewDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取笔记详情
     */
    @Override
    public Observable<BaseBean<BaseListBean<NotesDetailsBean>>> getnotesDetails(int notesId, int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getnotesDetails(notesId, pageNo, pageSize);
    }
    /**
     * 回复笔记
     */
    @Override
    public Observable<BaseBean> replyNotess(int notesId, String backContent,int courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).backContent(notesId, backContent, courseId);
    }
}