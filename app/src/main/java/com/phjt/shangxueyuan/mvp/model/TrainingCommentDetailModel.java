package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingCommentDetailContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class TrainingCommentDetailModel extends BaseModel implements TrainingCommentDetailContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public TrainingCommentDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<TrainingCommentBean.ReplyVoListBean>>> getCommentReplyList(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCommentReplyList(id);
    }

    @Override
    public Observable<BaseBean<String>> addCommentReply(String id, String replyContent) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .addCommentReply(id, replyContent);
    }

    @Override
    public Observable<BaseBean<String>> replyLike(String commentId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .replyLike(commentId);
    }

    @Override
    public Observable<BaseBean<String>> trainingCommentLike(String id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .trainingCommentLike(id);
    }
}