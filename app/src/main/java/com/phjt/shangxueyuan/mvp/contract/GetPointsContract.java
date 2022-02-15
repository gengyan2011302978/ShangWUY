package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.PointsDetailBean;
import com.phjt.shangxueyuan.bean.TaskListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 15:08
 */
public interface GetPointsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void loadIntegralRecordSuccess(BaseListBean<PointsDetailBean> bean, boolean isRefresh);

        void loadIntegralRecordFailure();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 查询积分明细
         *
         * @return
         */
        Observable<BaseBean<BaseListBean<PointsDetailBean>>> getIntegralRecord(int type, int detailType, int pageNo, int pageSize);
    }
}
