package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/26/2020 10:49
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface LoginContract {
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

        void checkLoginTypeSuccess(boolean show);
        /**
         * 滑块验证成功
         */
        void sliderValidationSuccess();
        /**
         * 滑块验证失败
         *
         * @param msg 错误信息
         */
        void sliderValidationFailed(String msg);

        /**
         * 登录成功
         *
         * @param bean 返回实体
         */
        void quickLoginSuccess(String bean);

        /**
         * 登录失败
         *
         * @param msg 错误信息
         */
        void quickLoginFailed(String msg);

        /**
         * 请求验证码成功
         */
        void getVerificationCodeSuccess();

        /**
         * 请求验证码失败
         *
         * @param msg 失败
         */
        void getVerificationCodeFailed(String msg);

        void getUserInfoSuccess(UserInfoBean userInfoBean);

        void showUpdateDialog(UpdateAppBean count);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<String>> loginByWeChat(String openId,String unionid);
        /**
         * 登录方式
         *
         * @return return
         */
        Observable<BaseBean<Boolean>> checkLoginType();
        /**
         * 星球登录
         *
         * @return return
         */
        Observable<BaseBean> doLoginByPlanetUuid(String uuid);
        /**
         * 滑块验证
         *
         * @param csessionid csessionid
         * @param sig        sig
         * @param token      token
         * @param scene      scene
         * @return return
         */
        Observable<BaseBean> sliderValidation(String csessionid, String sig, String token, String scene, String mobile);
        /**
         * 验证码获取
         *
         * @param mobile                  mobile
         * @param verificationcodeTypeSms verificationcodeTypeSms
         * @return return
         */
        Observable<BaseBean> getVerificationCode(String mobile, int verificationcodeTypeSms);
        /**
         * 短信登录
         *
         * @param mobile           mobile
         * @param verificationCode verificationCode
         * @return result
         */
        Observable<BaseBean> quickLogin(String mobile, String verificationCode);

        Observable<BaseBean<UserInfoBean>> getUserInfo();

        Observable<BaseBean<UpdateAppBean>> getCheckVersion();
    }
}
