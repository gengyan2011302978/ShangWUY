package com.phjt.shangxueyuan.mvp.presenter;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampDetailContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: 普华集团
 * description: 训练营详情页
 */
@ActivityScope
public class TrainingCampDetailPresenter extends BasePresenter<TrainingCampDetailContract.Model, TrainingCampDetailContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public TrainingCampDetailPresenter(TrainingCampDetailContract.Model model, TrainingCampDetailContract.View rootView) {
        super(model, rootView);
    }

    public Observable<BaseBean<BaseCourseListBean<DataBean>>> getDataList(String id, int currentPage, int pageSize, String courseType) {
        return mModel.getDataList(id, currentPage, pageSize, courseType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView));
    }

    public Observable<BaseBean<TrainingDetailBean>> getTraining(String id) {
        return mModel.getTrainingDetail(id)
                .compose(RxUtils.applySchedulers(mRootView));
    }

    /**
     * 获取训练营详情和资料个数 合并
     *
     * @param id 训练营id
     */
    public void getDataListAndTraining(String id) {
        Observable.zip(getDataList(id, 1, 10, "2"), getTraining(id),
                (baseBean, baseBean2) -> {
                    BaseCourseListBean<DataBean> baseCourseListBean = baseBean.data;
                    int totalCount = baseCourseListBean.getTotalCount();
                    TrainingDetailBean trainingDetailBean = baseBean2.data;
                    if (baseBean2.isOk()){
                        trainingDetailBean.setDataCount(totalCount);
                    }else {
                        TipsUtil.showTips(baseBean2.msg);
                        return new TrainingDetailBean();
                    }
                    return trainingDetailBean;
                }).doOnSubscribe(disposable -> mRootView.showLoading())
                .doFinally(() -> mRootView.hideLoading())
                .subscribe(new ErrorHandleSubscriber<TrainingDetailBean>(mErrorHandler) {
                    @Override
                    public void onNext(TrainingDetailBean detailBean) {
                        mRootView.showTitleDataAndTraining(detailBean);
                    }
                });
    }


    public void getTrainingDetail(String id) {
        mModel.getTrainingDetail(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<TrainingDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<TrainingDetailBean> baseBean) {
                        if (baseBean.isOk()) {
                            TrainingDetailBean trainingDetailBean = baseBean.data;
                            if (trainingDetailBean != null) {
                                mRootView.showTrainingDetail(trainingDetailBean);
                            } else {
                                mRootView.showMessage("训练营数据为空");
                            }
                        } else {
                            mRootView.showMessage(baseBean.msg);
                        }
                    }
                });
    }

    public void getTrainingShare(String id) {
        mModel.getTrainingShare(id)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<ShareBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<ShareBean> baseBean) {
                        if (baseBean.isOk()) {
                            ShareBean shareBean = baseBean.data;
                            if (shareBean != null) {
                                mRootView.showTrainingShare(shareBean);
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
