package com.phjt.shangxueyuan.mvp.model;


import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ReplyJournalContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Field;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/16 14:46
 */

@FragmentScope
public class ReplyJournalModel extends BaseModel implements ReplyJournalContract.Model {


    @Inject
    public ReplyJournalModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
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