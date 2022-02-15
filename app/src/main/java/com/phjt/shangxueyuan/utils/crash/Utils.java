package com.phjt.shangxueyuan.utils.crash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.phsxy.utils.LogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: gengyan
 * date:    2020/4/23
 * company: 普华集团
 * description: 描述
 */
public class Utils {

    public static CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        try {
            model.setEx(ex);
            model.setTime(System.currentTimeMillis());
            if (ex.getCause() != null) {
                ex = ex.getCause();
            }
            model.setExceptionMsg(ex.getMessage());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            String exceptionType = ex.getClass().getName();

            StackTraceElement element = parseThrowable(ex);
            if (element == null) {
                return model;
            }

            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);

            model.setFullException(sw.toString());

            model.setVersionCode(Utils.getVersionCode());
            model.setVersionName(Utils.getVersionName());
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    private static StackTraceElement parseThrowable(Throwable ex) {
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) {
            return null;
        }
        StackTraceElement element;
        String packageName = SpiderMan.getContext().getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

    static String getCachePath() {
        return SpiderMan.getContext().getCacheDir().getAbsolutePath();
    }

    private static String getVersionCode() {
        String versionCode = "";
        try {
            PackageManager pm = SpiderMan.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(SpiderMan.getContext().getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
        } catch (Exception e) {
            LogUtils.e("===================" + e.getMessage());
        }
        return versionCode;
    }

    private static String getVersionName() {
        String versionName = "";
        try {
            PackageManager pm = SpiderMan.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(SpiderMan.getContext().getPackageName(), 0);
            versionName = String.valueOf(packageInfo.versionName);
        } catch (Exception e) {
            LogUtils.e("===================" + e.getMessage());
        }
        return versionName;
    }

    /**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (nowTime.toString().equals(beginTime.toString())){
            return true;
        }
        if (nowTime.toString().equals(endTime.toString())){
            return true;
        }

        return date.after(begin) && date.before(end);
    }

    public static String numberFix(String studyNum){
        if (null == studyNum){
            return "0";
        }
        int mStudyNum = Integer.parseInt(studyNum);
        try {
            if (mStudyNum < 1000) {
                return studyNum;
            } else if (mStudyNum < 10000) {
                Double aDouble = new BigDecimal((float) mStudyNum / 1000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (aDouble.intValue() - aDouble == 0) {//判断是否符合取整条件
                    if (aDouble >= 10) {
                        return "1万";
                    }
                    return (aDouble.intValue() + "k");
                } else {
                    return (aDouble + "k");
                }
            } else if (mStudyNum < 100000) {
                Double aDouble = new BigDecimal((float) mStudyNum / 10000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (aDouble.intValue() - aDouble == 0) {//判断是否符合取整条件
                    return (aDouble.intValue() + "万");
                } else {
                    return (aDouble + "万");
                }
            } else if (mStudyNum == 100000) {
                return "10万";
            } else if (mStudyNum > 100000) {
                return "10万+";
            }
        } catch (Exception e) {

        }
        return studyNum;
    }
}
