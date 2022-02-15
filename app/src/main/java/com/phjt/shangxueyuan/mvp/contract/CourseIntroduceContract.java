package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.CourseCommentBean;
import com.phjt.shangxueyuan.bean.CourseCommentSizeBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:49
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CourseIntroduceContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void showCommentListRefresh(List<CourseCommentBean> commentBeans);

        void showCommentListLoadMore(List<CourseCommentBean> commentBeans);

        void canLoadMore();

        void cannotLoadMore();

        void stopRefreshAndLoadMore();

        void showEmptyView();

        void zanStateSuccess(CourseCommentBean commentBean, int position, int status);

        void getCourseCommentSizeSuccess(CourseCommentSizeBean baseBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取课程评论列表
         */
        Observable<BaseBean<BaseListBean<CourseCommentBean>>> getCourseCommentList(String courseId, String commentType, int currentPage, int pageSize);

        /**
         * 点赞 取消点赞
         */
        Observable<BaseBean> commentZanState(String commentId, int status);

        Observable<BaseBean<CourseCommentSizeBean>> getCourseCommentSize(String courseId);
    }
}
