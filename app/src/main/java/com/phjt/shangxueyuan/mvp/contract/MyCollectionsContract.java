package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */
public interface MyCollectionsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void loadDataSuccess(BaseListBean<MyCollectionBean> beans, boolean isRefresh);
        void loadDataFailure(boolean isRefresh);
        void loadFavoriteEditSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         *获取课程收藏列表
         */
        Observable<BaseBean<BaseListBean<MyCollectionBean>>> getCollectionList(int type,int pageNo, int  pageSize);

        /**
         *获取专题收藏记录列表
         */
        Observable<BaseBean<BaseListBean<MyCollectionBean>>> getSpecialFavouriteList(int pageNo, int  pageSize);

        /**
         * 收藏编辑
         * @param ids
         * @return
         */
        Observable<BaseBean> getFavoriteEdit(String ids,int mType);

    }
}
