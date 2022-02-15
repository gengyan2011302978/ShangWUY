package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.ActivityScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.mvp.contract.MyNotesContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;


@ActivityScope
public class MyNotesPresenter extends BasePresenter<MyNotesContract.Model, MyNotesContract.View> {

    @Inject
    AppManager mAppManager;

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MyNotesPresenter(MyNotesContract.Model model, MyNotesContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 获取笔记列表
     */
    public void getNotesList(int pageNo, int pageSize, boolean isRefresh) {
        mModel.getNotesList(pageNo, pageSize)
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyNotesBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyNotesBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadDataSuccess(bean.data, isRefresh);
                        } else {
                            mRootView.loadDataFailure( isRefresh);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadDataFailure(isRefresh);
                    }
                });
    }


    /**
     * 笔记点赞、取消点赞
     */
    public void thumbsUp(int notesId, int courseId, int position, int likeStatus) {
        mModel.thumbsUp(notesId, courseId)
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
        this.mAppManager = null;
        this.mErrorHandler = null;
    }
}
