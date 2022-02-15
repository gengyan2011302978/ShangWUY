package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.AddDiaryBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.mvp.contract.JournalContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */

@ActivityScope
public class JournalModel extends BaseModel implements JournalContract.Model {


    @Inject
    public JournalModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /*
     *主题详情
     * @return
     */
    @Override
    public Observable<BaseBean<MotifDetailBean>> getMotifDetails(String diaryId, String punchCardId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).motifDetail(diaryId, punchCardId);
    }

    /*
     *获取日记详情
     * @return
     */
    @Override
    public Observable<BaseBean<MyDiaryBean>> getDiaryDetails(String diaryId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getDiaryDetails(diaryId);
    }

    /*
     *日记发布/编辑
     * @return
     */
    @Override
    public Observable<BaseBean<AddDiaryBean>> addDiary(String diaryId, String punchCardId, String strEditDiary, int reissueCardType, String diaryImg,
                                                       String calendarDateString, String nodeTaskLinkId, String trainingCampId, String motifId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addDiary(diaryId, punchCardId, strEditDiary, reissueCardType, diaryImg,
                calendarDateString, nodeTaskLinkId, trainingCampId, motifId);
    }
}