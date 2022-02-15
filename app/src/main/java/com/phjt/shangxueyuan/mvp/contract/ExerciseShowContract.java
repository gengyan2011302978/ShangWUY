package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 03/11/2021 11:13
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface ExerciseShowContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void exerciseSuccess(BaseBean<ExerciseBean> baseBean);

        void exerciseAnswerSuccess(List<ExerciseShowBean> baseBean, String type);

        void exerciseAnswerLoadMore(List<ExerciseShowBean> baseBean, String type);

        void canLoadMore();

        void showShareItemDialog(ShareItemBean shareItemBean);

        void cannotLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<ExerciseBean>> exerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId);

        Observable<BaseBean<ShareItemBean>> getShareexErciseBook(String id, String couId, String trainingId);

        Observable<BaseBean<BaseListBean<ExerciseShowBean>>> userAnswer(String type, String exerciseId, String couId,
                                                                        String trainingId, int currentPage, int pageSize);
    }
}
