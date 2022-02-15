package com.phjt.shangxueyuan.mvp.presenter;


import com.phjt.base.di.scope.FragmentScope;
import com.phjt.base.http.errorhandler.core.RxErrorHandler;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.base.integration.AppManager;
import com.phjt.base.mvp.BasePresenter;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.RulesConfigBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;
import com.phjt.shangxueyuan.mvp.contract.MineContract;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.List;

import javax.inject.Inject;


@FragmentScope
public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {

    @Inject
    AppManager mAppManager;

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public MinePresenter(MineContract.Model model, MineContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取用户基本信息
     */
    public void getUserInfo() {
        mModel.getUserInfo()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserInfoBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserInfoBean> bean) {
                        if (bean.isOk()) {
                            UserInfoBean userInfoBean = bean.data;
                            if (userInfoBean != null) {
                                mRootView.loadDataSuccess(bean.data);
                            } else {
                                mRootView.showMessage("用户信息为空");
                            }
                        } else {
                            mRootView.showMessage(bean.msg);
                        }
                    }
                });
    }


    /**
     * 获取用户积分
     */
    public void getUserIntegral() {
        mModel.getUserIntegral()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAssetsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserAssetsBean> bean) {
                        if (bean.isOk()) {
                            mRootView.loadUserIntegralSuccess(bean.data);
                        } else {
                            TipsUtil.show(bean.msg);
                            mRootView.loadUserIntegralFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadUserIntegralFailure();
                    }
                });
    }

    /**
     * 请有礼分享接口
     */
    public void inviteShare() {
        mModel.inviteShare()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<ShareImgBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<ShareImgBean>> bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteShareSuccess(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 请有礼接口
     */
    public void inviteShareT() {
        mModel.inviteShareT()
                .compose(RxUtils.applySchedulersWithLoading(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean bean) {
                        if (bean.isOk()) {
                            mRootView.loadInviteSharetSuccess(String.valueOf(bean.data));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 是否显示弟子群
     */
    public void isShowDiscipleGroup(String code) {
        mModel.isShowDiscipleGroup(code)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<String> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.showDiscipleGroup(integerBaseBean.data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 提现规则
     */
    public void mShareRules(String type) {
        mModel.mShareRules(type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<RulesConfigBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<List<RulesConfigBean>> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.mShareRulesSuccess(integerBaseBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }


    /**
     * 获取我的话题
     */
    public void getTopicList() {
        mModel.getTopicList()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<BaseListBean<MyTopicBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<BaseListBean<MyTopicBean>> integerBaseBean) {
                        if (integerBaseBean.isOk()) {
                            mRootView.loadTopicSuccess(integerBaseBean.data);
                        } else {
                            mRootView.loadTopicFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mRootView.loadTopicFailed();
                    }
                });
    }


    /**
     * 是否展示启富通入口
     */
    public void isShowQftPointFlag() {
        mModel.isShowQftPointFlag()
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseBean<UserAuthBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseBean<UserAuthBean> integerBaseBean) {
                        if (integerBaseBean.isOk() && integerBaseBean.data != null) {
                            mRootView.isShowQftPointFlagSuccess(integerBaseBean.data.getStatus(),integerBaseBean.data.getFresh() );
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
