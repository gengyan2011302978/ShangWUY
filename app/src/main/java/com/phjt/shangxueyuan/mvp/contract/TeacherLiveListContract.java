package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveBean;
import com.phjt.shangxueyuan.bean.TeacherLiveBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 04/14/2021 13:50
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TeacherLiveListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void getTeacherLiveListSuccess(List<TeacherLiveBean> itemBeanList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<List<TeacherLiveBean>>> getTeacherLiveList();
    }
}
