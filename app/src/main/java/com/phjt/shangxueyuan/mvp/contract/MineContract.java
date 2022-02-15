package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BalanceBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.InitIndexSiteInfoBean;
import com.phjt.shangxueyuan.bean.MyPointsBean;
import com.phjt.shangxueyuan.bean.MyTopicBean;
import com.phjt.shangxueyuan.bean.RulesConfigBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;


public interface MineContract {

    interface View extends IBaseView {
        /**
         * 获取用户基本信息
         */
        void loadDataSuccess(UserInfoBean beans);

        void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data);
        void loadInviteSharetSuccess(String data);

        void showDiscipleGroup(String isShow);

        void mShareRulesSuccess(BaseBean<List<RulesConfigBean>> rulesConfigBeanBaseBean);


        void loadUserIntegralSuccess(UserAssetsBean  bean);
        void loadUserIntegralFailure();

        void loadTopicSuccess(BaseListBean<MyTopicBean>  bean);
        void loadTopicFailed();

        void isShowQftPointFlagSuccess(int status,int old);
    }

    interface Model extends IModel {
        /**
         * 获取用户基本信息
         */
        Observable<BaseBean<UserInfoBean>> getUserInfo();

        /**
         * 邀请用户接口
         *
         * @return
         */
        Observable<BaseBean<List<ShareImgBean>>> inviteShare();
        Observable<BaseBean> inviteShareT();

        /**
         * 是否显示弟子群
         *
         * @return
         */
        Observable<BaseBean<String>> isShowDiscipleGroup(String code);

        /**
         * 提现规则
         *
         * @return
         */
        Observable<BaseBean<List<RulesConfigBean>>> mShareRules(String type);



        /**
         * 查询个人总积分
         */
        Observable<BaseBean<UserAssetsBean>> getUserIntegral();

        /**
         * 获取我的话题列表
         * @return
         */
        Observable<BaseBean<BaseListBean<MyTopicBean>>> getTopicList();


        Observable<BaseBean<UserAuthBean>> isShowQftPointFlag();
    }
}
