package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MotifDiaryListBean;
import com.phjt.shangxueyuan.mvp.contract.HistoryThemeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 17:08
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class HistoryThemeModel extends BaseModel implements HistoryThemeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public HistoryThemeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<MotifDetailBean>> motifDetail(String id, String punchCardId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).motifDetail(id, punchCardId);
    }

    @Override
    public Observable<BaseBean<List<MotifDiaryListBean>>> diaryList(String id, String type, String motifId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).diaryList(id, type, motifId);
    }

    @Override
    public Observable<BaseBean> punchThumbsUp(String otherId, String otherType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).diaryPunchThumbsUp(otherId, otherType);
    }

    /*
     * 发布评论
     * @return
     */
    @Override
    public Observable<BaseBean> addComment(String bundleAddDiaryId, String punchCardId, String motifId, String commentId, String mContent) {

        Map<String, Object> map = new HashMap<>();
        map.put("diaryId", bundleAddDiaryId);
        map.put("cardId", punchCardId);
        map.put("motifId", motifId);
        map.put("commentId", commentId);
        map.put("replyContent", mContent);
        MediaType contentType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(contentType, new Gson().toJson(map));
        return mRepositoryManager.obtainRetrofitService(ApiService.class).addDiaryComment(requestBody);
    }

}