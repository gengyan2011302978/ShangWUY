package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.mvp.contract.ReplyJournalContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/16 14:46
 */

@FragmentScope
public class ReplyJournalPresenter extends BasePresenter<ReplyJournalContract.Model, ReplyJournalContract.View> {

    @Inject
    AppManager mAppManager;
    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public ReplyJournalPresenter(ReplyJournalContract.Model model, ReplyJournalContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 发布评论
     */
    public void addComment(String bundleAddDiaryId, String punchCardId,String motifId,String commentId, String mContent) {
        mModel.addComment(bundleAddDiaryId, punchCardId, motifId, commentId, mContent)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.addCommentSuccess();
                        } else {
                            mRootView.addCommentFail();
                            TipsUtil.show("评论失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.addCommentFail();
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
