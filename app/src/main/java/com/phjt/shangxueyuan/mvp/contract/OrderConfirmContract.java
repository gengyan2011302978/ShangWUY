package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 15:51
 * @description :
 */
public interface OrderConfirmContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showExchangeRatio(Double ratio);

        void paySuccess();

        void showCurrencyNotEnough();

        void showBoccNotEnough();

        void showUserAssetsAndPay(UserAssetsBean userAssetsBean);

        void isShowQftPointFlagSuccess(int status);

        void createStudyCoinOrderSuccess(String baseBean, int payMethod);

        void sendRequestYuOrderSuccess(AdvanceOrderBean data, String payType);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<Double>> getExchangeRatio();

        Observable<BaseBean> createTrainingOrder(String commodityId, int buyNum, int payMethod, double commodityMoney, int type);

        Observable<BaseBean> exchangeVirtualCommodity(String virtualCommodityId, int payType, int quantity);

        Observable<BaseBean> answersConfirmOrder(String teacherId, int payType, int quantity);

        Observable<BaseBean> getAddOrder(String commodityId, int payType, int quantity);

        Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo();

        Observable<BaseBean<UserAuthBean>> isShowQftPointFlag();

        Observable<BaseBean<String>> createStudyCoinOrder(String commodityMoney, int payMethod);

        Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(String orderId, String payType);
    }
}