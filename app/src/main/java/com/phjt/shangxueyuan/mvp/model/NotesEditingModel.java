package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.NotesEditingContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */

@FragmentScope
public class NotesEditingModel extends BaseModel implements NotesEditingContract.Model {


    @Inject
    public NotesEditingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 添加笔记
     *
     * @param courseId
     * @param backContent
     * @param openState
     * @return
     */
    @Override
    public Observable<BaseBean> addNotess(String courseId, String pointId, String backContent, int openState, String notesImg, long coursePauseTime, int mType, String courseType) {
        if (mType == 0) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).addNotess(courseId, pointId, backContent,
                    openState, notesImg, coursePauseTime, 1, courseType);
        } else {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).editNotes(courseId, pointId, backContent, openState, notesImg, coursePauseTime);
        }
    }

    @Override
    public Observable<BaseBean> addComment(String courseId, String content, String imgs, String courseType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .addComment(courseId, content, imgs, courseType);
    }

    @Override
    public Observable<BaseBean<String>> addTrainingComment(String punchCardId, String otherId, String content, String img) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .addTrainingComment(punchCardId, otherId, content, img);
    }


}