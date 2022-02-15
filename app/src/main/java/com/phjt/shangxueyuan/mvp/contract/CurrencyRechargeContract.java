package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/25 09:39
 * @description :
 */
public interface CurrencyRechargeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void createStudyCoinOrderSuccess(String baseBean,int payMethod);

        void createStudyCoinOrderFailed(String msg);
        void sendRequestYuOrderSuccess(AdvanceOrderBean data,String payType);
        void showUserAssets(UserAssetsBean assetsBean);
        void sendRequestYuOrderFailed(String msg);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<String>> createStudyCoinOrder(String commodityMoney, int payMethod);
        Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(String orderId, String payType);
        Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo();
    }
}