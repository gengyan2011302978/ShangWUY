package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogOneBean;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;
import com.phjt.shangxueyuan.bean.CourseDetailAndCatalogBean;
import com.phjt.shangxueyuan.bean.CourseDetailBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.VipStateBean;
import com.phjt.shangxueyuan.mvp.contract.CourseDetailContract;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 14:34
 * company: 普华集团
 * description: 课程详情页
 */
@ActivityScope
public class CourseDetailPresenter extends BasePresenter<CourseDetailContract.Model, CourseDetailContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public CourseDetailPresenter(CourseDetailContract.Model model, CourseDetailContract.View rootView) {
        super(model, rootView);
    }

    public Observable<BaseBean<CourseDetailBean>> getCourseDetail(String courseId) {
        return mModel.getCourseDetail(courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView));
    }

    public Observable<BaseBean<List<CourseCatalogFirstBean>>> getCourseCatalogList(String courseId) {
        return mModel.getCourseCalalogList(courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView));
    }

    public void getCourseDetailAndCatalog(String courseId) {
        mModel.getCourseDetailAndCatalog(courseId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<CourseDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<CourseDetailBean> baseBean) {
                        if (!baseBean.isOk()) {
                            mRootView.showMessage(baseBean.msg);
                            return;
                        }

                        CourseDetailAndCatalogBean courseDetailAndCatalogBean1 = new CourseDetailAndCatalogBean((CourseDetailBean) baseBean.data);
                        if (!"1".equals(courseDetailAndCatalogBean1.getCourseDetailBean().getCouType())) {
                            mModel.getCourseCalalogList(courseId)
                                    .compose(RxUtils.applySchedulersWithLoading(mRootView))
                                    .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseCatalogFirstBean>>>(mErrorHandler) {
                                        @Override
                                        public void onNext(BaseBean<List<CourseCatalogFirstBean>> baseBean) {
                                            courseDetailAndCatalogBean1.setCatalogFirstBeans(baseBean.data);
                                            mRootView.showCourseDetailAndCatalog(courseDetailAndCatalogBean1, courseDetailAndCatalogBean1.getCourseDetailBean().getCouType());
                                            mRootView.dismissLoadingDialog();
                                        }
                                    });
                        } else {
                            mModel.getCourseCalalogListZ(courseId)
                                    .compose(RxUtils.applySchedulersWithLoading(mRootView))
                                    .subscribe(new ErrorHandleSubscriber<BaseBean<List<CourseCatalogOneBean>>>(mErrorHandler) {
                                        @Override
                                        public void onNext(BaseBean<List<CourseCatalogOneBean>> baseBean) {
                                            List<CourseCatalogFirstBean> courseCatalogFirstBeans = new ArrayList<>();
                                            List<CourseCatalogSecondBean> courseCatalogSecondBeans;
                                            for (int i = 0; i < baseBean.data.size(); i++) {
                                                CourseCatalogFirstBean courseCatalogFirstBean = new CourseCatalogFirstBean();
                                                CourseCatalogOneBean courseCatalogOneBean = baseBean.data.get(i);
                                                courseCatalogFirstBean.setCoursewareName(courseCatalogOneBean.getName());
                                                courseCatalogFirstBean.setCouDescribe(courseCatalogOneBean.getCouDescribe());
                                                courseCatalogSecondBeans = new ArrayList<>();
                                                for (int j = 0; j < courseCatalogOneBean.getCouWareVOS().size(); j++) {
                                                    CourseCatalogOneBean.CouWareVOSBean couWareVOSBean = courseCatalogOneBean.getCouWareVOS().get(j);
                                                    CourseCatalogSecondBean courseCatalogSecondBean = new CourseCatalogSecondBean();
                                                    for (int k = 0; k < couWareVOSBean.getCouPointVOs().size(); k++) {
                                                        courseCatalogSecondBean = couWareVOSBean.getCouPointVOs().get(k);
                                                        if (k < 1) {
                                                            courseCatalogSecondBean.setTitle(courseCatalogOneBean.getCouWareVOS().get(j).getCoursewareName());
                                                            courseCatalogFirstBean.setCoursewareId(courseCatalogOneBean.getCouWareVOS().get(j).getCoursewareId());
                                                        }
                                                        courseCatalogSecondBean.setCourseId(courseCatalogOneBean.getCouId());
                                                        courseCatalogSecondBeans.add(courseCatalogSecondBean);
                                                    }
                                                }
                                                courseCatalogFirstBean.setCouPointVOs(courseCatalogSecondBeans);
                                                courseCatalogFirstBeans.add(courseCatalogFirstBean);
                                            }
                                            courseDetailAndCatalogBean1.setCatalogFirstBeans(courseCatalogFirstBeans);
                                            mRootView.showCourseDetailAndCatalog(courseDetailAndCatalogBean1, "1");
                                            mRootView.dismissLoadingDialog();
                                        }
                                    });
                        }
                    }
                });
    }
//    public void getCourseDetailAndCatalog1(String courseId) {
//        mRootView.showLoadingDialog();
//        Observable.zip(getCourseDetail(courseId), getCourseCatalogList(courseId), (courseDetailBaseBean, listBaseBean) -> {
//            CourseDetailBean courseDetailBean = courseDetailBaseBean.data;
//            List<CourseCatalogFirstBean> catalogFirstBeans = listBaseBean.data;
//
//            return new CourseDetailAndCatalogBean(courseDetailBean, catalogFirstBeans);
//        }).doOnSubscribe(disposable -> mRootView.showLoading())
//                .doFinally(() -> mRootView.hideLoading())
//                .subscribe(new ErrorHandleSubscriber<CourseDetailAndCatalogBean>(mErrorHandler) {
//                    @Override
//                    public void onNext(CourseDetailAndCatalogBean courseDetailAndCatalogBean) {
//                        mRootView.showCourseDetailAndCatalog(courseDetailAndCatalogBean);
//                        mRootView.dismissLoadingDialog();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mRootView.dismissLoadingDialog();
//                    }
//                });
//    }
//    public void getCourseDetailAndCatalog1(String courseId,String types) {
//        mRootView.showLoadingDialog();
//        Observable.concat(getCourseDetail(courseId), getCourseCatalogList(courseId)).subscribeWith(new ErrorHandleSubscriber<BaseBean<?>>() {
//            @Override
//            public void onNext(BaseBean<?> baseBean) {
//                CourseDetailAndCatalogBean courseDetailAndCatalogBean = new CourseDetailAndCatalogBean((CourseDetailBean) baseBean.data);
//                if (courseDetailAndCatalogBean.getCourseDetailBean().getCouType().equals("1")){
//
//                }
//            }
//        });
//    }

    public void getCourseShareData(String courseId, int type,String courseType,String pointId) {
        mModel.getCourseShareData(courseId,courseType,pointId)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareBean> baseBean) {
                        if (baseBean.isOk()) {
                            ShareBean shareBean = baseBean.data;
                            if (shareBean != null) {
                                mRootView.showShareDialog(shareBean, type);
                            } else {
                                mRootView.showMessage("获取分享数据为空");
                            }
                        } else {
                            if (Constant.LOGOUT_CODE_ERROR != baseBean.code) {
                                mRootView.showMessage(baseBean.msg);
                            }
                        }
                    }
                });
    }

    public void doFavorite(String couId, String courseType) {
        mModel.doFavorite(couId, courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        if (baseBean.isOk()) {
                            mRootView.doFavoriteSuccess();
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    /**
     * 获取vip状态
     *
     * @param isFirst true:首次进入  false:播放判断
     */
    public void getVipState(boolean isFirst) {
        mModel.getVipState()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<VipStateBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<VipStateBean> baseBean) {
                        if (baseBean.isOk()) {
                            VipStateBean vipStateBean = baseBean.data;
                            if (vipStateBean != null) {
                                if (isFirst) {
                                    mRootView.showVipState(vipStateBean);
                                } else {
                                    //0.普通用户；1.普通vip；2.永久vip；3.vip已过期
                                    Integer vipState = vipStateBean.getVipState();
                                    if (vipState == 1 || vipState == 2) {
                                        mRootView.showVipVideoPlay();
                                    } else {
                                        mRootView.showVipBugDialog();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 退出是调用
     */
    public void saveWatchTime(String courseId, String pointId, String wareId, long watchDuration, int endFlag, String columnId) {
        mModel.saveWatchTime(courseId, pointId, wareId, watchDuration, endFlag, columnId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        mRootView.saveTimeSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.saveTimeSuccess();
                    }
                });
    }

    /**
     * 60s上报调用，不处理返回结果
     */
    public void reportWatchTime(String courseId, String pointId, String wareId, long watchDuration, int endFlag, String columnId) {
        mModel.saveWatchTime(courseId, pointId, wareId, watchDuration, endFlag, columnId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean baseBean) {

                    }
                });
    }


    public void getShareItemData(int type, String otherId, String content, String couType) {
        mModel.getShareItemData(type, otherId, content, couType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareItemBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareItemBean> baseBean) {
                        if (baseBean.isOk()) {
                            ShareItemBean shareItemBean = baseBean.data;
                            if (shareItemBean != null) {
                                mRootView.showShareItemDialog(shareItemBean);
                            } else {
                                mRootView.showMessage("分享数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
    }
}
