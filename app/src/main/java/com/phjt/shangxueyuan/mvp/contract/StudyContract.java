package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseChannelBean;
import com.phjt.shangxueyuan.bean.CourseTypeBean;
import com.phjt.shangxueyuan.bean.OrdinaryCourseBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 05/07/2020 14:09
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface StudyContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void getCourseTypeSuccess(List<CourseTypeBean> data);
        void getCourseTypeFailed(String msg);
        void getChannelCourseSuccess(List<CourseChannelBean> data);
        void getChannelCourseFailed(String msg);
        void getOrdinaryCourseSuccess(List<OrdinaryCourseBean> data);
        void getResearchColumnSuccess(List<OrdinaryCourseBean> data);
        void getOrdinaryCourseFailed(String msg);
        void getResearchColumnFailed(String msg);
        void loadDataSuccess(UserInfoBean beans);
        void loadError();
        /**
         * 获取训练营记录
         * @return
         */
        void getTrainingBattalionSuccess(List<TrainingBattalionBean> beans);
        void getTrainingBattalionFailed();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<List<CourseTypeBean>>> getCourseType();

        Observable<BaseBean<List<CourseChannelBean>>> getChannelCourse(String couTypeId);

        Observable<BaseBean<List<OrdinaryCourseBean>>> getOrdinaryCourse();

        Observable<BaseBean<List<OrdinaryCourseBean>>> researchColumn();

        Observable<BaseBean<UserInfoBean>> getUserInfo();

        /**
         * 获取训练营记录
         * @return
         */
        Observable<BaseBean<List<TrainingBattalionBean>>> getTrainingBattalion();
    }
}
