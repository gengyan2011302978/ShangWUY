package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.FragmentScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.InvitationRecordBean;
import com.phjt.shangxueyuan.mvp.contract.MyInvitationContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 08/28/2020 10:41
 * company: 普华集团
 * description: 模版请保持更新
 */
@FragmentScope
public class MyInvitationModel extends BaseModel implements MyInvitationContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public MyInvitationModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<InvitationRecordBean>>> getInvitationRecordList() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getInvitationRecordList();
    }
}