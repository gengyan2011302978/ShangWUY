package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CheckTheAnswerBean;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/22 11:05
 * @description :
 */
public interface CheckTheAnswerContract {

    interface View extends IBaseView {
        /**
         * 查看答案成功
         *
         * @param bean
         */
        void checkTheAnswerSuccess(CheckTheAnswerBean bean);

        void checkTheAnswerFail();


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存

    interface Model extends IModel {
        /**
         * 查看答案成功
         */
        Observable<BaseBean<CheckTheAnswerBean>> getUserQuestion(String isRecommend);

    }
}