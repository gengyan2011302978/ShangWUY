package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CouponBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/11/24 14:11
 */
public interface ExchangeVoucherContract {

    interface View extends IBaseView {
        void getInitIndexSiteInfoListSuccess(List<InitIndexSiteInfoBean> bean);
        void getExchangeCouponListSuccess(BaseListBean<CouponBean> bean,boolean isRefresh);
        void getExchangeCouponListFailed(boolean isRefresh);
    }

    interface Model extends IModel {
        /**
         * 精品试听
         */
        Observable<BaseBean<List<InitIndexSiteInfoBean>>> getInitIndexSiteInfo();

        /**
         * 可用兑换礼券列表
         */
        Observable<BaseBean<BaseListBean<CouponBean>>> getExchangeCouponList(int type,int currentPage,int pageSize);
    }
}
