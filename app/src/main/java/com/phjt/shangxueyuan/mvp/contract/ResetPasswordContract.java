package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;

import io.reactivex.Observable;


/**
 * @author: Created by Template
 * date: 03/27/2020 13:40
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface ResetPasswordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void submitSuccess();

        void submitFailed(String msg);

        void loadOutLoginSuccess();

        void loadOutLoginFailed();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean> resetPassword(String phone,String newPassWord);

        Observable<BaseBean> changePassword(String phone,String newPassWord);

        Observable<BaseBean> outLogin();
    }
}
