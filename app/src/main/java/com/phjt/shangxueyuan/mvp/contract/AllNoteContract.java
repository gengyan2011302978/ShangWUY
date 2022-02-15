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
 * @date : 2020/06/04 11:19
 */
public interface AllNoteContract {

    interface View extends IBaseView {
        void loadDataSuccess(BaseListBean<MyNotesBean> beans, boolean isRefresh);
        void loadDataFailure();


    }

    interface Model extends IModel {
        /**
         * 获取笔记列表
         */
        Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(String courseId, int pageNo, int  pageSize);

    }
}
