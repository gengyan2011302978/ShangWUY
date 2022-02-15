package com.phjt.shangxueyuan.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.bean.event.ConnectBean;
import com.phjt.shangxueyuan.bean.event.WebToStudyBean;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.NetworkUtils;

/**
 * @author: gengyan
 * date:    2020/4/13 15:25
 * company: 普华集团
 * description: 网络监听
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!NetworkUtils.isConnected()) {
            EventBusManager.getInstance().post(new ConnectBean(false));

            LogUtils.e("============= 网络不可用");
        } else {
            EventBusManager.getInstance().post(new ConnectBean(true));
            LogUtils.e("============= 当前网络已连接");
        }
    }
}
