package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:16
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface ForgetPasswordContract {
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

        void sendVerficationSuccess();

        void sendVerficationFailed(String msg);

        void loginSuccess(String token);

        void loginFailed(String msg);

        void validateCodeSuccess();

        void validateCodeFailed(String msg);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean> sendCode(String phone, int codeType);

        Observable<BaseBean> sliderValidation(String csessionid, String sig, String token, String scene,String mobile ,int codeType);

        Observable<BaseBean<String>> bindingPhone(String mobile, String openId, String code, String imgUrl, String userName,String unionid);

        Observable<BaseBean> validateCode(String phone, String code, int codeType);
    }
}
