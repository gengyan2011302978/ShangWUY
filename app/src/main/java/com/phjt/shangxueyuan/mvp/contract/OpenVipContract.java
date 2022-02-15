package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.AdvanceOrderBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.PayMethodBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 15:37
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface OpenVipContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void requestOrderDetailSuccess(String bean);

        void requestOrderDetailFailed(String msg);

        void sendRequestYuOrderSuccess(AdvanceOrderBean data);

        void sendRequestYuOrderFailed(String msg);

        void getPayMethodSuccess(List<PayMethodBean> data);

        void getPayMethodFailed(String msg);

        void showCoupon(CouponBean couponBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<String>> requestOrderDetail(String commodityId, int payMethod, int activityState, String userCouponId, int type);

        Observable<BaseBean<AdvanceOrderBean>> sendRequestYuOrder(String orderId, String payType);

        Observable<BaseBean<List<PayMethodBean>>> getPayMethod();

        /**
         * 获取优惠券信息
         */
        Observable<BaseBean<List<CouponBean>>> getCouponList(String commodityTyId, int type, int activityState);
    }
}
