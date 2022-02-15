package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */
public interface ParticipatingPunchContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void loadSuccess(BaseListBean<ParticipatingPunchBean> integerBaseBean, boolean isReFresh);

        void loadFailed(boolean isReFresh);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取我参与的打卡列表
         */
        Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getMyPunchCardList(int currentPage, int pageSize);

        /**
         * 获取课程 打卡列表
         */
        Observable<BaseBean<BaseListBean<ParticipatingPunchBean>>> getPunchClockList(String couId, String couType, int currentPage, int pageSize);
    }
}
