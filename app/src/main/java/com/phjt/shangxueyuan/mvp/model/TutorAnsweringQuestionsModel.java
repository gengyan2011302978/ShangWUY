package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;
import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.mvp.contract.TutorAnsweringQuestionsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
@FragmentScope
public class TutorAnsweringQuestionsModel extends BaseModel implements TutorAnsweringQuestionsContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TutorAnsweringQuestionsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    /**
     * 获取导师信息列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getTutorInfoList(int pageNo, int pageSize, String realmId, int mType) {
        if (mType == 0) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getAnswerTutorInfoList(pageNo, pageSize, realmId);
        } else {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).getTutorInfoList(pageNo, pageSize, realmId);
        }
    }


    /**
     * 校验用户学分是否足够支付提问
     */
    @Override
    public Observable<BaseBean> checkUserCapital(String mTutorId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).checkUserCapital(mTutorId);
    }

    /**
     * 查询精选解答列表信息
     */
    @Override
    public Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getMyConsultationList(int pageNo, int pageSize, String isRecommend, int timeSort, int likeSort) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getMyConsultationList(pageNo, pageSize, isRecommend, timeSort, likeSort);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }
}