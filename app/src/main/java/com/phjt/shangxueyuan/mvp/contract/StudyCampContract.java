package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.StudyCampBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/23 09:41
 */
public interface StudyCampContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showStudyCampRefresh(List<StudyCampBean> commentBeans);

        void showStudyCampLoadMore(List<StudyCampBean> commentBeans);

        void canLoadMore();

        void cannotLoadMore();

        void showEmptyView();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 成长营列表
         */
        Observable<BaseBean<BaseListBean<StudyCampBean>>> getStudyCampList(String trainingCampType, int currentPage, int pageSize);
    }
}
