package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IntroductionPunchCardsBean;
import com.phjt.shangxueyuan.bean.IntroductionTopCardsBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.bean.SaveGeneratePicturesBean;
import com.phjt.shangxueyuan.mvp.contract.IntroductionPunchCardsContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */

@ActivityScope
public class IntroductionPunchCardsModel extends BaseModel implements IntroductionPunchCardsContract.Model {


    @Inject
    public IntroductionPunchCardsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 打卡介绍
     *
     * @return
     */
    @Override
    public Observable<BaseBean<IntroductionPunchCardsBean>> getIntroductionCardst(String punchCardId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getIntroductionCardst(punchCardId);
    }

    /**
     * 获取打卡主页顶部焦点图
     *
     * @return
     */
    @Override
    public Observable<BaseBean<IntroductionTopCardsBean>> getHomeFocus(String punchCardId, String couId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getHomeFocus(punchCardId, couId);
    }


}