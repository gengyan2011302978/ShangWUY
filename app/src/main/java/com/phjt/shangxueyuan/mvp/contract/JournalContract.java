package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.AddDiaryBean;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 14:49
 */
public interface JournalContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {

        void addDiarySuccess(BaseBean<AddDiaryBean> addDiaryBean);

        void getMotifDetails(MotifDetailBean bean);
        void getDiaryDetails(MyDiaryBean bean);

        void diaryDetailsFailed();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取主题详情详情
         *
         * @return
         */
        Observable<BaseBean<MotifDetailBean>> getMotifDetails(String diaryId, String punchCardId);
        /**
         * 获取日记详情
         *
         * @return
         */
        Observable<BaseBean<MyDiaryBean>> getDiaryDetails(String diaryId);

        /**
         * 日记发布/编辑
         *
         * @return
         */
        Observable<BaseBean<AddDiaryBean>> addDiary(String diaryId, String punchCardId, String strEditDiary, int reissueCardType,
                                                    String diaryImg, String calendarDateString, String nodeTaskLinkId, String trainingCampId, String motifId);
    }
}
