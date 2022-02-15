package com.phjt.shangxueyuan.utils.encryption;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.BatteryManager;
import android.os.Process;

import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.encryption.CommandUtil;
import com.phsxy.utils.SPUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author: Roy
 * date:   2021/5/21
 * company: 普华集团
 * description:
 */
public class SecurityCheckUtil {
    /**
     * 获取签名信息
     */
    public static String getSignature(Context context) {
        try {
            PackageInfo packageInfo = context.
                    getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            // 通过返回的包信息获得签名数组
            Signature[] signatures = packageInfo.signatures;
            // 循环遍历签名数组拼接应用签名
            StringBuilder builder = new StringBuilder();
            for (Signature signature : signatures) {
                builder.append(signature.toCharsString());
            }
            // 得到应用签名
            return builder.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检测app是否为debug版本
     */
    public static boolean checkIsDebugVersion(Context context) {
        return (context.getApplicationInfo().flags
                & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * java法检测是否连上调试器
     */
    public static boolean checkIsDebuggerConnected() {
        return android.os.Debug.isDebuggerConnected();
    }

    /**
     * usb充电辅助判断
     */
    public static boolean checkIsUsbCharging(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        if (batteryStatus == null) {
            return false;
        }
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
    }

    /**
     * 拿清单值
     */
    public static String getApplicationMetaValue(Context context, String name) {
        ApplicationInfo appInfo = context.getApplicationInfo();
        return appInfo.metaData.getString(name);
    }

    /**
     * 检测本地端口是否被占用
     */
    public static boolean isLocalPortUsing(int port) {
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 检测任一端口是否被占用
     */
    public static boolean isPortUsing(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress theAddress = InetAddress.getByName(host);
        try {
            Socket socket = new Socket(theAddress, port);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断手机是否root
     */
    public static boolean isRoot() {
        if (SPUtils.getInstance().contains(Constant.SP_IS_ROOT)) {
            //避免再次计算
            return SPUtils.getInstance().getBoolean(Constant.SP_IS_ROOT);
        }
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        try {
            if (new File(binPath).exists() && isCanExecute(binPath)) {
                SPUtils.getInstance().put(Constant.SP_IS_ROOT, true);
                return true;
            }
            if (new File(xBinPath).exists() && isCanExecute(xBinPath)) {
                SPUtils.getInstance().put(Constant.SP_IS_ROOT, true);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        SPUtils.getInstance().put(Constant.SP_IS_ROOT, false);
        return false;
    }

    private static boolean isCanExecute(String filePath) {
        java.lang.Process process = null;
        try {
            process = Runtime.getRuntime().exec("ls -l " + filePath);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x') {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 检查root权限1
     */
    public boolean isRoot1() {
        int secureProp = getroSecureProp();
        //eng/userdebug版本，自带root权限
        if (secureProp == 0) {
            return true;
        } else {
            //user版本，继续查su文件
            return isSuExist();
        }
    }

    private int getroSecureProp() {
        int secureProp;
        String roSecureObj = CommandUtil.getSingleInstance().getProperty("ro.secure");
        if (roSecureObj == null) {
            secureProp = 1;
        } else {
            if ("0".equals(roSecureObj)) {
                secureProp = 0;
            } else {
                secureProp = 1;
            }
        }
        return secureProp;
    }

    private int getroDebugProp() {
        int debugProp;
        String roDebugObj = CommandUtil.getSingleInstance().getProperty("ro.debuggable");
        if (roDebugObj == null) {
            debugProp = 1;
        } else {
            if ("0".equals(roDebugObj)) {
                debugProp = 0;
            } else {
                debugProp = 1;
            }
        }
        return debugProp;
    }

    private static boolean isSuExist() {
        File file;
        String[] paths = {"/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"};
        for (String path : paths) {
            file = new File(path);
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    private static final String XPOSED_HELPERS = "de.robv.android.xposed.XposedHelpers";
    private static final String XPOSED_BRIDGE = "de.robv.android.xposed.XposedBridge";

    /**
     * 通过检查是否已经加载了XP类来检测
     */
    @Deprecated
    public static boolean isXposedExists() {
        try {
            Object xpHelperObj = ClassLoader
                    .getSystemClassLoader()
                    .loadClass(XPOSED_HELPERS)
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try {
            Object xpBridgeObj = ClassLoader
                    .getSystemClassLoader()
                    .loadClass(XPOSED_BRIDGE)
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 通过主动抛出异常，检查堆栈信息来判断是否存在XP框架
     */
    public static boolean isXposedExistByThrow() {
        try {
            throw new Exception("gg");
        } catch (Exception e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().contains(XPOSED_BRIDGE)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 尝试关闭XP框架
     * 先通过isXposedExistByThrow判断有没有XP框架
     * 有的话先hookXP框架的全局变量disableHooks
     * <p>
     * 漏洞在，如果XP框架先hook了isXposedExistByThrow的返回值，那么后续就没法走了
     * 现在直接先hookXP框架的全局变量disableHooks
     *
     * @return 是否关闭成功的结果
     */
    public static boolean tryShutdownXposed() {
        Field xpdisabledHooks = null;
        try {
            xpdisabledHooks = ClassLoader.getSystemClassLoader()
                    .loadClass(XPOSED_BRIDGE)
                    .getDeclaredField("disableHooks");
            xpdisabledHooks.setAccessible(true);
            xpdisabledHooks.set(null, Boolean.TRUE);
            return true;
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检测有么有加载so库
     */
    public static boolean hasReadProcMaps(String paramString) {
        try {
            Object localObject = new HashSet();
            BufferedReader localBufferedReader =
                    new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/maps"));
            for (; ; ) {
                String str = localBufferedReader.readLine();
                if (str == null) {
                    break;
                }
                if ((str.endsWith(".so")) || (str.endsWith(".jar"))) {
                    ((Set) localObject).add(str.substring(str.lastIndexOf(" ") + 1));
                }
            }
            localBufferedReader.close();
            localObject = ((Set) localObject).iterator();
            while (((Iterator) localObject).hasNext()) {
                boolean bool = ((String) ((Iterator) localObject).next()).contains(paramString);
                if (bool) {
                    return true;
                }
            }
        } catch (Exception fuck) {
            fuck.printStackTrace();
        }
        return false;
    }

    /**
     * java读取/proc/uid/status文件里TracerPid的方式来检测是否被调试
     */
    public static boolean readProcStatus() {
        try {
            BufferedReader localBufferedReader =
                    new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/status"));
            String tracerPid;
            for (; ; ) {
                String str = localBufferedReader.readLine();
                if (str.contains("TracerPid")) {
                    tracerPid = str.substring(str.indexOf(":") + 1).trim();
                    break;
                }
            }
            localBufferedReader.close();
            return !"0".equals(tracerPid);
        } catch (Exception fuck) {
            return false;
        }
    }
}
