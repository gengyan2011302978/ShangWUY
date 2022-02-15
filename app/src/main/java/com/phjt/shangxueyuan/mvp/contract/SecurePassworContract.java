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
 * @date : 2021/05/12 11:18
 */
public interface SecurePassworContract {
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


        void modifySuccess();

        void modifyFailed();


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean> sliderValidation(String csessionid, String sig, String token, String scene,String mobile ,int codeType);
        Observable<BaseBean> modifyPassword(String mobile, String originalPwd, String newPassword, String repeatNewPwd, String verificationCode,int type);

    }
}
