package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseCourseListBean;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TrainingCampDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showTrainingDetail(TrainingDetailBean detailBean);

        void showTrainingShare(ShareBean shareBean);

        void showTitleDataAndTraining(TrainingDetailBean detailBean);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<TrainingDetailBean>> getTrainingDetail(String id);

        Observable<BaseBean<ShareBean>> getTrainingShare(String id);

        Observable<BaseBean<String>> trainingFreeBuy(String trainingCampId);

        Observable<BaseBean<BaseCourseListBean<DataBean>>> getDataList(String courseId,
                                                                       int currentPage, int pageSize, String courseType);
    }
}
