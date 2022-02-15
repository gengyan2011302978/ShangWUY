package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.IntroductionPunchCardsBean;
import com.phjt.shangxueyuan.bean.IntroductionTopCardsBean;
import com.phjt.shangxueyuan.bean.ParticipatingPunchBean;
import com.phjt.shangxueyuan.bean.SaveGeneratePicturesBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/18 10:20
 */
public interface IntroductionPunchCardsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void LoadSuccess(IntroductionPunchCardsBean integerBaseBean);

        void LoadFailed();

        void LoadPunchCardsSuccess(IntroductionTopCardsBean integerBaseBean);

        void LoadPunchCardsFailed();




    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 打卡介绍
         *
         * @return
         */
        Observable<BaseBean<IntroductionPunchCardsBean>> getIntroductionCardst(String punchCardId);

        /**
         * 获取打卡主页顶部焦点图
         */
        Observable<BaseBean<IntroductionTopCardsBean>> getHomeFocus(String punchCardId, String couId);

    }
}
