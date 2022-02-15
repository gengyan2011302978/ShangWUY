package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.MyTrainingCampBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:14
 */
public interface TrainingCampContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void LoadSuccess(BaseListBean<MyTrainingCampBean> integerBaseBean, boolean isReFresh);
        void LoadFailed(boolean isReFresh);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取我的专栏/训练营列表
         * @param currentPage
         * @param pageSize
         * @return
         */
        Observable<BaseBean<BaseListBean<MyTrainingCampBean>>> getTrainingBattalionList(int type,int currentPage, int pageSize);
    }
}
