package com.phjt.shangxueyuan.mvp.model;

import android.app.Application;

import com.phjt.base.integration.IRepositoryManager;
import com.phjt.base.mvp.BaseModel;

import com.phjt.base.di.scope.ActivityScope;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogOneBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.VipStateBean;
import com.phjt.shangxueyuan.mvp.contract.CourseDetailContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 模版请保持更新
 */
@ActivityScope
public class CourseDetailModel extends BaseModel implements CourseDetailContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public CourseDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<CourseDetailBean>> getCourseDetail(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseDetail(courseId);
    }

    @Override
    public Observable<BaseBean<ShareBean>> getCourseShareData(String courseId,String type,String pointId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseShareData(courseId,type,pointId);
    }

    @Override
    public Observable<BaseBean<CourseDetailBean>> getCourseDetailAndCatalog(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseDetail(courseId);
    }

    @Override
    public Observable<BaseBean> doFavorite(String couId, String courseType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .doFavorite(couId, courseType);
    }

    @Override
    public Observable<BaseBean<VipStateBean>> getVipState() {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getVipState();
    }

    @Override
    public Observable<BaseBean> saveWatchTime(String courseId, String pointId, String wareId, long watchDuration, int endFlag, String columnId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .saveWatchTime(courseId, pointId, wareId, watchDuration, endFlag, columnId);
    }

    @Override
    public Observable<BaseBean<List<CourseCatalogFirstBean>>> getCourseCalalogList(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCalalogList(courseId);
    }

    @Override
    public Observable<BaseBean<List<CourseCatalogOneBean>>> getCourseCalalogListZ(String courseId) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getCourseCalalogListZ(courseId);
    }

    @Override
    public Observable<BaseBean<ShareItemBean>> getShareItemData(int type, String otherId, String content, String couType) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class)
                .getShareItemData(type, otherId, content, 0, couType);
    }
}