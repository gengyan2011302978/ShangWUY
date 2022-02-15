package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
public interface TutorAnsweringQuestionsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showWithdrawalRecordRefresh(List<TutorAnsweringQuestionsBean> withdrawalRecordBeans);

        void showWithdrawalRecordLoadMore(List<TutorAnsweringQuestionsBean> withdrawalRecordBeans);


        void canLoadMore();

        void cannotLoadMore();

        void stopRefreshAndLoadMore();

        void showEmptyView();


        /**
         * 校验用户学分是否足够支付提问
         */
        void checkUserCapitalSuccess(TutorAnsweringQuestionsBean bean);

        void checkUserCapitalFail(String questionCoin);

    }

    /**
     * Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
     */
    interface Model extends IModel {
        /**
         * 获取导师信息列表
         */
        Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getTutorInfoList(int pageNo, int pageSize, String realmId, int mType);

        /**
         * 校验用户学分是否足够支付提问
         */
        Observable<BaseBean> checkUserCapital(String mTutorId);

        Observable<BaseBean<BaseListBean<TutorAnsweringQuestionsBean>>> getMyConsultationList( int pageNo, int pageSize, String isRecommend, int timeSort, int likeSort);
    }
}