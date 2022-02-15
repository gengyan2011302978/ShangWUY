package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.TopicItemInfoBean;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoListContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class TopicInfoListModel extends BaseModel implements TopicInfoListContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TopicInfoListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<TopicItemInfoBean>> themeList(int pageNo, int pageSize, String type, String topicId, String topicUserId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).themeList(pageNo, pageSize, type, topicId, topicUserId);
    }

    @Override
    public Observable<BaseBean> themeLike(String themeId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).themeLike(themeId);
    }

    @Override
    public Observable<BaseBean> themeDelete(String themeId, String topicId, String topicUserId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).themeDelete(themeId, topicId, topicUserId);
    }

    @Override
    public Observable<BaseBean> themeTop(String themeId, String isTop, String topicId, String topicUserId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).themeTop(themeId, isTop, topicId, topicUserId);
    }

    /**
     * 话题分享  couType无用，传默认值
     */
    @Override
    public Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getShareItemData(type, otherId, content, 0, String.valueOf(0));
    }
}