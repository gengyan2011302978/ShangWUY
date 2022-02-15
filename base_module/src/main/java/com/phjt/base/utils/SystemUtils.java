package com.phjt.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import java.util.List;

public class SystemUtils {
    /**
     * 返回应用主进程名称
     *
     * @param context {@link Context}
     * @return Main Process Name
     */
    public static String getMainProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null || runningAppProcesses.isEmpty()) {
            return null;
        }
        int processId = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo processInfo :
                runningAppProcesses) {
            if (processInfo.pid == processId) {
                return processInfo.processName;
            }
        }
        return null;
    }

}
