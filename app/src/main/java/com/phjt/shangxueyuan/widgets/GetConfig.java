package com.phjt.shangxueyuan.widgets;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.phjt.shangxueyuan.R;

import java.io.File;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

public class GetConfig {

    public static final String UPDATE_APKNAME = "GfanMobile.apk";
    public static final String UPDATE_SAVENAME = "yjk.apk";

    /**
     * 功能说明：获取程序版本号：
     * */
    public static int getVerCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 功能说明：获取程序版本名称：
     * */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        Log.e("verName","verName"+verName);
        return verName;
    }

    public static void Instanll(File file, Context context){
        Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileprovider", file);
        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            File filee= new File(String.valueOf(file));
            //在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        } else {
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
//                Uri apkUri1 = Uri.parse("file://" + apkUri);
                apkUri = Uri.fromFile(file);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            }catch (ActivityNotFoundException a){
                a.getMessage();
            }

        }
    }

    /**
     * 功能说明：获取程序名称
     * */
    public static String getAppName(Context context) {
        String appName = "";
        appName = context.getResources().getText(R.string.app_name).toString();
        return appName;
    }

    /**
     * 功能说明：取消通知
     * */
    public static void cancelNotif(Context context, int notification_id) {
        NotificationManager nma = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nma.cancel(notification_id);
    }

    public static boolean requestPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };
            int checkSPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int checkSPermission2 = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if ((checkSPermission != PackageManager.PERMISSION_GRANTED)
                    || (checkSPermission2 != PackageManager.PERMISSION_GRANTED)
                    ) {
                ActivityCompat.requestPermissions((Activity) context, mPermissionList, 11);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public static String getverson(){
     return    Environment.getExternalStorageDirectory().getPath() + "/shangxueyuan/download/shangwuyou.apk";
    }
}
