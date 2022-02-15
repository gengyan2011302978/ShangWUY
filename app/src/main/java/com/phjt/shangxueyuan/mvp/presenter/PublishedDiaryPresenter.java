package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.mvp.contract.PublishedDiaryContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

/**
 * Created by Template
 *
 * @author :
 * description :我的打卡-参与的打卡
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

@FragmentScope
public class PublishedDiaryPresenter extends BasePresenter<PublishedDiaryContract.Model, PublishedDiaryContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public PublishedDiaryPresenter(PublishedDiaryContract.Model model, PublishedDiaryContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取我的日记列表
     */
    public void getMyDiaryList(int currentPage, int pageSize, boolean isReFresh) {
        mModel.getMyDiaryList(currentPage, pageSize)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyDiaryBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyDiaryBean>> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.LoadSuccess(integerBaseBean.data, isReFresh);
                        } else {
                            mRootView.LoadFailed(isReFresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.LoadFailed(isReFresh);
                    }
                });
    }

    /**
     * 删除日记
     */
    public void delectDiary(String diaryId) {
        mModel.delectDiary(diaryId)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.delectDiarySuccess();
                        } else {
                            TipsUtil.show("删除失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    /**
     * 日记点赞、取消点赞
     */
    public void thumbsUp(String otherId, int otherType, int position, int likeStatus) {
        mModel.thumbsUp(otherId, otherType)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.thumbsUpSuccess(position, likeStatus);
                        } else {
                            TipsUtil.show(bean.msg);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
