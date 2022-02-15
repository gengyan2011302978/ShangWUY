package com.phjt.shangxueyuan.mvp.contract;

import com.phjt.base.mvp.IBaseView;
import com.phjt.base.mvp.IModel;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;

import io.reactivex.Observable;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/17 09:45
 */
public interface ScannerContract {

    interface View extends IBaseView {

        void loadDataSuccess();

        void loadDataFailure();
    }

    interface Model extends IModel {
        /**
         * 扫一扫
         */
        Observable<BaseBean> getScanQRcode(String certificat);

        /**
         * 扫一扫-确认/取消
         */
        Observable<BaseBean> getScanRcodeConfirm(String certificate, int optionType);
    }
}
