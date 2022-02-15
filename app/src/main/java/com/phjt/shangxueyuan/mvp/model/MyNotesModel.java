package com.phjt.shangxueyuan.mvp.model;


import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.mvp.contract.MyNotesContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import io.reactivex.Observable;


@ActivityScope
public class MyNotesModel extends BaseModel implements MyNotesContract.Model {


    @Inject
    public MyNotesModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    /**
     * 获取笔记列表
     */
    @Override
    public Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(int pageNo, int pageSize) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).getNotesList(pageNo, pageSize);
    }

    /**
     * 笔记点赞、取消点赞
     */
    @Override
    public Observable<BaseBean> thumbsUp(int notesId, int courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).thumbsUp(notesId, courseId);
    }

    @Override
    public Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, String couType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getShareItemData(type, otherId, content, 0, couType);
    }
}