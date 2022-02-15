package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 11:32
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CourseCommentContract {
    interface View extends IBaseView {

        void showCommentListRefresh(List<CourseCommentBean> commentBeans);

        void showCommentListLoadMore(List<CourseCommentBean> commentBeans);

        void canLoadMore();

        void cannotLoadMore();

        void stopRefreshAndLoadMore();

        void showEmptyView();

        void addCommentSuccess();

        void zanStateSuccess(CourseCommentBean commentBean, int position, int status);
    }

    interface Model extends IModel {
//        /**
//         * 获取课程评论列表
//         */
//        Observable<BaseBean<BaseListBean<CourseCommentBean>>> getCourseCommentList(String courseId, int currentPage, int pageSize);
//
//        /**
//         * 添加课程评论
//         */
//        Observable<BaseBean> addCommentSuccess(String courseId, String content);
//
//        /**
//         * 点赞 取消点赞
//         */
//        Observable<BaseBean> commentZanState(String commentId, int status);
    }
}
