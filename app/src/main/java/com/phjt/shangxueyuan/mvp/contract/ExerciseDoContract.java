package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.ExerciseBean;
import com.phjt.shangxueyuan.bean.ExerciseSubmitBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;


/**
 * @author: Created by GengYan
 * date: 03/16/2021 09:46
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface ExerciseDoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void showExerciseList(ExerciseBean exerciseBean, boolean isSubmitSuccess);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseBean<ExerciseBean>> getExerciseBookDetail(String id, String couId, String exerciseBookId, String trainingId);

        Observable<BaseBean<ExerciseBean>> submitExerciseAnswer(List<ExerciseSubmitBean> submitBeanList);
    }
}
