package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.RegisterBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;

import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 10:20
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
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
         * 注册成功
         *
         * @param bean 返回实体
         */
        void registerSuccess(String bean);

        /**
         * 注册失败
         *
         * @param msg 错误信息
         */
        void registerFailed(String msg);

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
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 滑块验证
         *
         * @param csessionid csessionid
         * @param sig        sig
         * @param token      token
         * @param scene      scene
         * @return return
         */
        Observable<BaseBean> sliderValidation(String csessionid, String sig, String token, String scene,String mobile);

        Observable<BaseBean> userRegister(String mobile, String verificationCode, String password);

        Observable<BaseBean> getVerificationCode(String mobile, int verificationcodeTypeReg);
    }
}
