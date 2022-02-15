package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogOneBean;
import com.phjt.shangxueyuan.bean.CourseDetailAndCatalogBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.VipStateBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CourseDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showCourseDetailAndCatalog(CourseDetailAndCatalogBean courseDetailAndCatalogBean, String type);

        void showShareDialog(ShareBean shareBean, int type);

        void doFavoriteSuccess();

        void showVipState(VipStateBean vipStateBean);

        void saveTimeSuccess();

        void showLoadingDialog();

        void dismissLoadingDialog();

        void showShareItemDialog(ShareItemBean shareItemBean);

        void showVipVideoPlay();

        void showVipBugDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<CourseDetailBean>> getCourseDetail(String couresId);

        Observable<BaseBean<ShareBean>> getCourseShareData(String courseId,String courseType,String pointId);

        Observable<BaseBean<CourseDetailBean>> getCourseDetailAndCatalog(String courseId);

        Observable<BaseBean> doFavorite(String couId, String courseType);

        Observable<BaseBean<VipStateBean>> getVipState();

        Observable<BaseBean> saveWatchTime(String courseId, String pointId, String wareId, long watchDuration, int endFlag, String columnId);

        Observable<BaseBean<List<CourseCatalogFirstBean>>> getCourseCalalogList(String courseId);

        Observable<BaseBean<List<CourseCatalogOneBean>>> getCourseCalalogListZ(String courseId);

        /**
         * 动态、课程评论、笔记、专题留言分享
         */
        Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, String couType);
    }
}
