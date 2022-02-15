package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.ThemeMainBean;
import com.phjt.shangxueyuan.bean.TopicMainBean;
import com.phjt.shangxueyuan.mvp.contract.CircleContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/19/2020 10:24
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class CircleModel extends BaseModel implements CircleContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CircleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<TopicMainBean>>> recommendTopics() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .recommendTopics();
    }

    @Override
    public Observable<BaseBean> themeLike(String themeSource, String themeId, String likeStatus, String commentId, String notesId, String leaveId, String courseId) {
        if (themeSource.equals("1")) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class)
                    .commentZanState(commentId, Integer.parseInt(likeStatus));
        }
        if (themeSource.equals("2")) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class)
                    .thumbsUp(Integer.parseInt(notesId), Integer.parseInt(courseId));
        }
        if (themeSource.equals("3")) {
            return mRepositoryManager.obtainRetrofitService(ApiService.class)
                    .insertLeaveWordLike(Integer.parseInt(leaveId), Integer.parseInt(likeStatus));
        }
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .themeLike(themeId);
    }


    @Override
    public Observable<BaseBean<List<ListBannerBean>>> ListBanner(String bannerType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).ListBanner("image/v1.0.0/listBanner",bannerType, 1);
    }

    @Override
    public Observable<BaseBean<ThemeMainBean>> getRefreshList(String themeId, int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getRefreshList(themeId, pageNo, pageSize);
    }

    @Override
    public Observable<BaseBean<String>> addTopic(int type) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addTopic(type, "", "", "");
    }

    @Override
    public Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, int shareType, String couType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getShareItemData(type, otherId, content, shareType, couType);
    }
}