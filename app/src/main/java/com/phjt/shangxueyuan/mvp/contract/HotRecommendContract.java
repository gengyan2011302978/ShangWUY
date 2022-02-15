package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.CourseItemBean;
import com.phjt.shangxueyuan.bean.CourseTeacherItemBean;
import com.phjt.shangxueyuan.bean.LiveBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/27 17:21
 */
public interface HotRecommendContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showAuditionCourseRefresh(List<CourseItemBean> itemBeanList);

        void showAuditionCourseLoadMore(List<CourseItemBean> itemBeanList);


        void canLoadMore();

        void cannotLoadMore();

        void stopRefreshAndLoadMore();

        void showEmptyView();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<BaseCourseListBean<CourseItemBean>>> getRecommendList( int currentPage, int pageSize);
    }
}
