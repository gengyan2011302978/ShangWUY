package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MineExerciseBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * @author: Created by GengYan
 * date: 03/22/2021 16:26
 * company: 普华集团
 * description: 模版请保持更新
 */
public interface ExerciseListContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void showCommentListRefresh(List<MineExerciseBean> commentBeans);

        void showCommentListLoadMore(List<MineExerciseBean> commentBeans);

        void canLoadMore();

        void cannotLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseBean<BaseListBean<MineExerciseBean>>> exerciseBookList(String couId,String couType, int currentPage, int pageSize);
    }
}
