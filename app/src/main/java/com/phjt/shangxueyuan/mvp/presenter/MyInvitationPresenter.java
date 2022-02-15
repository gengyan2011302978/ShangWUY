package com.phjt.shangxueyuan.mvp.presenter;

import android.app.Application;

import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.InvitationRecordBean;
import com.phjt.shangxueyuan.mvp.contract.MyInvitationContract;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.List;


/**
 * @author: Created by GengYan
 * date: 08/28/2020 10:41
 * company: 普华集团
 * description: 我的邀请
 */
@FragmentScope
public class MyInvitationPresenter extends BasePresenter<MyInvitationContract.Model, MyInvitationContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyInvitationPresenter(MyInvitationContract.Model model, MyInvitationContract.View rootView) {
        super(model, rootView);
    }

    public void getInvitationRecordList() {
        mModel.getInvitationRecordList()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<InvitationRecordBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<InvitationRecordBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<InvitationRecordBean> recordBeans = baseBean.data;
                            if (recordBeans != null && !recordBeans.isEmpty()) {
                                mRootView.showInvitationRecord(recordBeans);

                                int invitationedNum = 0;
                                for (int i = 0; i < recordBeans.size(); i++) {
                                    InvitationRecordBean recordBean = recordBeans.get(i);
                                    if (recordBean.getActivatedState() == 0) {
                                        invitationedNum += 1;
                                    }
                                }
                                mRootView.showInvitationedNum(invitationedNum);

                            } else {
                                mRootView.showEmptyView();
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
