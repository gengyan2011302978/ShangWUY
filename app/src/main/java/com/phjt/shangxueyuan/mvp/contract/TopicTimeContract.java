package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.TopicListBean;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TopicTimeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void loadRefreshDataSuccess(TopicListBean beans, int pageNo, boolean isRefresh);
        void loadRefreshDataFailure();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<TopicListBean>> getTopicList(int pageNo, int pageSize);
    }
}
