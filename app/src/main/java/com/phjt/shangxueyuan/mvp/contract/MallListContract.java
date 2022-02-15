package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MallCommodityBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 16:22
 * @description :
 */
public interface MallListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showCommodityRefresh(List<MallCommodityBean> commodityBeanList);

        void showCommodityLoadMore(List<MallCommodityBean> commodityBeanList);

        void canLoadMore();

        void cannotLoadMore();

        void stopRefreshAndLoadMore();

        void showEmptyView();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<BaseListBean<MallCommodityBean>>> getMallCommodityList(int commodityType, int currentPage, int pageSize);

        Observable<BaseBean<BaseListBean<MallCommodityBean>>> getExchangeRecordList(int currentPage, int pageSize);
    }
}