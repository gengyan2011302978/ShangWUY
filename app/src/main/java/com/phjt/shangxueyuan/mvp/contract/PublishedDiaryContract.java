package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */
public interface PublishedDiaryContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void LoadSuccess(BaseListBean<MyDiaryBean> integerBaseBean, boolean isReFresh);
        void LoadFailed(boolean isReFresh);
        void delectDiarySuccess();
        void thumbsUpSuccess(int position, int likeStatus);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 获取我的日记列表
         * @param currentPage
         * @param pageSize
         * @return
         */
        Observable<BaseBean<BaseListBean<MyDiaryBean>>> getMyDiaryList(int currentPage, int pageSize);
        /**
         * 删除日记
         * @return
         */
        Observable<BaseBean> delectDiary(String diaryId);

        /**
         * 日记点赞、取消点赞
         */
        Observable<BaseBean> thumbsUp(String notesId, int courseId);
    }
}
