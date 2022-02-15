package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UpdateAppBean;
import com.phjt.shangxueyuan.bean.UserAuthBean;

import io.reactivex.Observable;


public interface SetUpContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void showUpdateDialog(UpdateAppBean count);
        void LoadFailed();
        void loadOutLoginSuccess();
        void loadOutLoginFailed();
        void userAuthSuccess(int status);
        void passwordSuccess(int status);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<UpdateAppBean>> getCheckVersion();
        Observable<BaseBean> outLogin();
        Observable<BaseBean<UserAuthBean>> isUserAuth(int validType);
    }
}
