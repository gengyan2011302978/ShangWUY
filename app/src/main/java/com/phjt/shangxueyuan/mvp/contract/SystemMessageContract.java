package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.MessageListBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/10/20 10:52
 */
public interface SystemMessageContract {
    /**
     * 对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
     */
    interface View extends IBaseView {

        void getRefresh(List<MessageListBean.RecordsBean> bean);
        void getLoadMore(List<MessageListBean.RecordsBean> bean);
        void canLoadMore();
        void cannotLoadMore();
        void stopRefreshAndLoadMore();
        void getLoadFail();

    }

    /**
     * Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
     */
    interface Model extends IModel {
        Observable<BaseBean<MessageListBean>> getListMessage(int type, int pageNo, int pageSize);
    }
}
