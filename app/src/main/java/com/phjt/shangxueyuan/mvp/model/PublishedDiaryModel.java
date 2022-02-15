package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.mvp.contract.PublishedDiaryContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
public class PublishedDiaryModel extends BaseModel implements PublishedDiaryContract.Model {


    @Inject
    public PublishedDiaryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /* 获取我参与的打卡列表
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyDiaryBean>>> getMyDiaryList(int currentPage, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMyDiaryList(currentPage, pageSize);
    }

    /* 删除日记
     *
     * @return
     */
    @Override
    public Observable<BaseBean> delectDiary(String diaryId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).delectDiary(diaryId);
    }

    /**
     * 日记点赞、取消点赞
     */
    @Override
    public Observable<BaseBean> thumbsUp(String notesId, int courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).diaryPunchThumbsUp(notesId, String.valueOf(courseId));
    }

}