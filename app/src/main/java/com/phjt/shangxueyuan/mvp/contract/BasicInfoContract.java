package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.UserInfoBean;

import io.reactivex.Observable;


public interface BasicInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        /**
         * 获取用户基本信息
         */
        void loadDataSuccess(UserInfoBean beans);
        void userEditSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         *获取用户基本信息
         */
        Observable<BaseBean<UserInfoBean>> getUserInfo();
        /**
         *编辑用户基本信息
         */
        Observable<BaseBean> onClickUserEdit(String nickName,String userName,int sex,String photo);
    }
}
