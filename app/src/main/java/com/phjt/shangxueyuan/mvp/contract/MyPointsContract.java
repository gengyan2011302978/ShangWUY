package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.bean.TaskCurrencyFirstBean;
import com.phjt.shangxueyuan.bean.UserAssetsBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/21 10:54
 */
public interface MyPointsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showUserAssets(UserAssetsBean assetsBean);

        void loadTaskLisSuccess(List<TaskCurrencyFirstBean> bean);

        void loadInviteSharetSuccess(String data);

        void loadInviteShareSuccess(BaseBean<List<ShareImgBean>> data,String url);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<UserAssetsBean>> getUserAssetsInfo();

        Observable<BaseBean<List<TaskCurrencyFirstBean>>> getNewTaskList();

        /**
         * 邀请用户接口
         *
         * @return
         */
        Observable<BaseBean<List<ShareImgBean>>> inviteShare();
        Observable<BaseBean> inviteShareT();
    }
}
