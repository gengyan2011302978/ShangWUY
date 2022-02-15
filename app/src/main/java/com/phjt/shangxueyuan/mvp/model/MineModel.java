package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BalanceBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.MyPointsBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.RulesConfigBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.MineContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;

@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {


    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取用户基本信息
     */
    @Override
    public Observable<BaseBean<UserInfoBean>> getUserInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).UserInfoBean();
    }

    /**
     * 邀请有礼接口
     */
    @Override
    public Observable<BaseBean<List<ShareImgBean>>> inviteShare() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShare();

    }

    @Override
    public Observable<BaseBean> inviteShareT() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).inviteShareT();
    }

    @Override
    public Observable<BaseBean<String>> isShowDiscipleGroup(String code) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getConfig(code);
    }

    @Override
    public Observable<BaseBean<List<RulesConfigBean>>> mShareRules(String type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).mShareRules(type);
    }

    /**
     * 查询个人总积分
     */
    @Override
    public Observable<BaseBean<UserAssetsBean>> getUserIntegral() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getUserAssetsInfo();
    }

    /**
     * 获取我的话题列表
     *
     * @return
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyTopicBean>>> getTopicList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getTopicList(1, 10);
    }


    @Override
    public Observable<BaseBean<UserAuthBean>> isShowQftPointFlag() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).isShowQftPointFlag();
    }
}