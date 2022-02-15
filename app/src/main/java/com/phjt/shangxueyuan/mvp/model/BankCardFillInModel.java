package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BankCardFillBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.BankCardFillInContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author: Created by GengYan
 * date: 08/04/2020 15:15
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class BankCardFillInModel extends BaseModel implements BankCardFillInContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public BankCardFillInModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean> saveBankCardInfo(BankCardFillBean bankCardFillBean) {
        Map<String, Object> map = new HashMap<>();
        map.put("cardholder", bankCardFillBean.getCardholder());
        map.put("cardNumber", bankCardFillBean.getCardNumber());
        map.put("bankName", bankCardFillBean.getBankName());
        map.put("bankAddress", bankCardFillBean.getBankAddress());
        map.put("cardholderPhone", bankCardFillBean.getCardholderPhone());

        MediaType contentType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody requestBody = RequestBody.create(contentType, new Gson().toJson(map));
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .saveBankCardInfo(requestBody);
    }
}