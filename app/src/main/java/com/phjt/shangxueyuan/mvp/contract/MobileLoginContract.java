package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;

import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/30/2020 10:42
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface MobileLoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void loginSuccess(String token);

        void loginFailedByWx(String msg);

        /**
         * 登录失败
         *
         * @param message 错误信息
         */
        void doLoginFailed(String message);

        /**
         * 登录成功
         */
        void doLoginSuccess(String token);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 登录
         *
         * @param mobile   mobile
         * @param password password
         * @return return
         */
        Observable<BaseBean> doLogin(String mobile, String password);
        Observable<BaseBean<String>> loginByWeChat(String openId,String unionid);
    }
}
