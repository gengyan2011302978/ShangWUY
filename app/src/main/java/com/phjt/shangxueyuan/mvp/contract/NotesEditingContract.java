package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/09/17 18:54
 */
public interface NotesEditingContract {
    interface View extends IBaseView {

        void addNotessSuccess();

        void addNotessFail();

        void addCommentSuccess();

        void addTrainingCommentSuccess();
    }

    interface Model extends IModel {
        /**
         * 新增笔记
         */
        Observable<BaseBean> addNotess(String courseId, String pointId, String backContent, int openState, String notesImg, long coursePauseTime, int mType, String courseType);

        /**
         * 新增课程评论
         */
        Observable<BaseBean> addComment(String courseId, String content, String imgs, String courseType);

        /**
         * 训练营 新增评论
         */
        Observable<BaseBean<String>> addTrainingComment(String punchCardId, String otherId, String content, String img);
    }
}
