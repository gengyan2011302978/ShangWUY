package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 01/20/2021 11:57
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface TrainingPlayContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showTrainingComment(List<TrainingCommentBean.DiaryListBean> diaryList);

        void showTrainingCommentEmpty();

        void trainingCommentLikeSuccess(TrainingCommentBean.DiaryListBean commentBean, int position);

        void updateTimeSuccess();

        void playEndAndRefreshTrainingData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<TrainingCommentBean>> getTrainingComment(String id);

        Observable<BaseBean<String>> trainingCommentLike(String id);

        Observable<BaseBean<String>> updateTaskById(String id);

        Observable<BaseBean<String>> updateTaskRecordById(String trainingCampId, String secondId, int status, long watchTime);
    }
}
