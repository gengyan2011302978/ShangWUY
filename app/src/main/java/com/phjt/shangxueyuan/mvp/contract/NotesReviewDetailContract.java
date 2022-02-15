package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/18 13:44
 */
public interface NotesReviewDetailContract {

    interface View extends IBaseView {

        void loadDetailsSuccess(BaseListBean<NotesDetailsBean> beans, boolean refurbish);
        void loadDetailsFailure(boolean refurbish);
        void replyNotesSuccess();

    }


    interface Model extends IModel {
        /**
         * 获取笔记详情
         */
        Observable<BaseBean<BaseListBean<NotesDetailsBean>>> getnotesDetails(int notesId, int pageNo, int pageSize);

        /**
         * 回复笔记
         * @param notesId
         * @return
         */
        Observable<BaseBean> replyNotess(int notesId, String backContent,int courseId);

    }
}
