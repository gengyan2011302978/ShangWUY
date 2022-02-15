package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseClassifyBean;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 17:36
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CourseClassifyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息

    interface View extends IBaseView {
        void loadDataSuccess(CourseClassifyBean bean);

        void loadDataFail();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存

    interface Model extends IModel {
        /**
         * 证书频道详情
         * @return
         */
        Observable<BaseBean<CourseClassifyBean>> getCourseClassifyList(String couTypeId);

    }
}
