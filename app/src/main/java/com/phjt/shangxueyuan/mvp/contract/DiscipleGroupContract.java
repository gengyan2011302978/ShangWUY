package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/03 17:45
 */
public interface DiscipleGroupContract {
    /**
     * 对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
     */
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
         * 发送验证码成功
         */
        void sendVerficationSuccess();

        /**
         * 发送验证码失败
         * @param msg
         */
        void sendVerficationFailed(String msg);

    }

    /**
     * Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
     */
    interface Model extends IModel {
        /**
         * 滑块验证
         * @param csessionid
         * @param sig
         * @param token
         * @param scene
         * @return
         */
        Observable<BaseBean> sliderValidation(String csessionid, String sig, String token, String scene,String mobile);

        /**
         * 发送验证码
         * @param phone
         * @param codeType
         * @return
         */
        Observable<BaseBean> sendCode(String phone, int codeType);

        /**
         *弟子群绑定
         */
        Observable<BaseBean> getDiscipleGroupAuth(String phone, String code);
    }
}
