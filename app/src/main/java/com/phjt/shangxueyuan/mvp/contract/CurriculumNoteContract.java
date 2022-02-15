package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.NotesDetailsBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 11:19
 */
public interface CurriculumNoteContract {

    interface View extends IBaseView {

        void thumbsUpSuccess(int position, int likeStatus);
        void loadRefreshDataSuccess(BaseListBean<MyNotesBean> beans,int pageNo, boolean isRefresh);
        void loadRefreshDataFailure();


    }


    interface Model extends IModel {
        /**
         * 获取笔记列表
         */
        Observable<BaseBean<BaseListBean<MyNotesBean>>> getNotesList(String courseId, String pointId,int pageNo, int pageSize,int type,String courseType);

        /**
         * 笔记点赞、取消点赞
         */
        Observable<BaseBean> thumbsUp(int notesId, int courseId);
    }
}
