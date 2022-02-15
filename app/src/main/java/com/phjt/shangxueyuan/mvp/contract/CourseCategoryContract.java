package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.CourseCategoryBean;
import com.phjt.shangxueyuan.bean.OnDemandBean;
import com.phjt.shangxueyuan.bean.SuspendImgBean;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 05/06/2020 13:55
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface CourseCategoryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void getCourseCategory(CourseCategoryBean categoryBean);

        void isShowNoNetworkView(boolean isShow);

        void showOnDemandInfo(OnDemandBean onDemandBean);
        void showSuspendImgInfo(SuspendImgBean onDemandBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<CourseCategoryBean>> getCourseCategory(@Field("id") String id);

        Observable<BaseBean<OnDemandBean>> getOnDemandBean();

        Observable<BaseBean<SuspendImgBean>> getSuspendImg();
    }
}
