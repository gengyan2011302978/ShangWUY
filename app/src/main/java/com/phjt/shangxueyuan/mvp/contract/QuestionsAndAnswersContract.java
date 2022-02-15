package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ScreenLabelBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 10:56
 * @description :
 */
public interface QuestionsAndAnswersContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        /**
         * 筛选
         */

        void loadDataSuccess(List<ScreenlBean> beans);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 筛选
         */
        Observable<BaseBean<List<ScreenlBean>>> getRealmSelectList();
    }
}