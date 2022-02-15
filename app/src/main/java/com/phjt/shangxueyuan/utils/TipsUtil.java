package com.phjt.shangxueyuan.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.shangxueyuan.R;
import com.phsxy.utils.AppContextUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;

/**
 * @author yanmengjun
 */
public class TipsUtil {

    private static Toast toast;

    private static View toastView;

    public static void showTips(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        showMes(msg);
    }

    public static void show(String msg) {
        showTips(msg);
    }

    /**
     * 显示toast
     *
     * @param message 显示文本内容
     */
    private static void showMes(String message) {
        if (TextUtils.isEmpty(message) || message.trim().isEmpty() || AppContextUtil.getInstance() == null) {
            return;
        }

        //过滤短时间内相同的提示
        long delay = 2000;
        String msg = SPUtils.getInstance().getString("temp_msg", message);
        long currentTimeMillis = System.currentTimeMillis();
        long lastTime = SPUtils.getInstance().getLong("temp_msg_m", currentTimeMillis - delay);
        if (message.equals(msg) && currentTimeMillis - lastTime < delay) {
            LogUtils.d("<", delay);
            return;
        }
        SPUtils.getInstance().put("temp_msg", message);
        SPUtils.getInstance().put("temp_msg_m", currentTimeMillis);


        if (toast != null) {
            toast.cancel();
        }

        if (toastView == null) {
            toastView = LayoutInflater.from(AppContextUtil.getInstance()).inflate(R.layout.toast_layout, null);
        }


        TextView textView = toastView.findViewById(R.id.tv_toast);
        textView.setText(message);
        toast = new Toast(AppContextUtil.getInstance());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastView);
        toast.show();


    }

}
