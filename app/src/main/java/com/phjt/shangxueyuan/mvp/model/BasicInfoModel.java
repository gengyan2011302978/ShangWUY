package com.phjt.shangxueyuan.mvp.model;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.BasicInfoContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;


@ActivityScope
public class BasicInfoModel extends BaseModel implements BasicInfoContract.Model {


    @Inject
    public BasicInfoModel(IRepositoryManager repositoryManager) {
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
     * 修改用户基本信息
     */
    @Override
    public Observable<BaseBean> onClickUserEdit( String nickName, String userName, int sex,String photo) {
        if (!TextUtils.isEmpty(photo)){
            return mRepositoryManager.obtainRetrofitService(ApiService.class).onClickUserEditPhoto(nickName,userName,sex,photo);
        }else {
            return mRepositoryManager.obtainRetrofitService(ApiService.class).onClickUserEdit(nickName,userName,sex);
        }
    }
}