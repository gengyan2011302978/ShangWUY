package com.phjt.shangxueyuan.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ShareActivity;

import java.util.List;

import static com.phjt.shangxueyuan.utils.Constant.BUNDLE_COURSE_ID;

public class AppUtils {
    public static boolean isPlanetInstalled(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.phjt.ecologyplanet", 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void goPlanetAppForLoginRequest(Context context) {
        if (isPlanetInstalled(context)) {
            ComponentName componentName = new ComponentName("com.phjt.ecologyplanet", "com.phjt.ecologyplanet.mvp.ui.activity.SplashActivity");
            Intent intent = new Intent();
            intent.putExtra("is_auth_request", true);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }
    }

    public static void wakeUpPlanet(Context context, boolean isAuthRequest) {
        if (AppUtils.isPlanetInstalled(context)) {
            if (isAuthRequest) {
                goPlanetAppForLoginRequest(context);
            } else {
                openPackage(context, "com.phjt.ecologyplanet");
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(BuildConfig.PLANET_DOWNLOAD_URL));
            context.startActivity(intent);
        }
    }

    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        //Activity完整名
        String mainAct = null;
        //根据包名寻找
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
        intent.setComponent(new ComponentName(packageName, mainAct));
        return intent;
    }

    public static Context getPackageContext(Context context, String packageName) {
        Context pkgContext = null;
        if (context.getPackageName().equals(packageName)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(packageName,
                        Context.CONTEXT_IGNORE_SECURITY
                                | Context.CONTEXT_INCLUDE_CODE);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return pkgContext;
    }

    public static boolean openPackage(Context context, String packageName) {
        Context pkgContext = getPackageContext(context, packageName);
        Intent intent = getAppOpenIntentByPackageName(context, packageName);
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent);
            return true;
        }
        return false;
    }

    public static void goDetailPage(Activity context, String linkType, String linkAddress) {
        if (!TextUtils.isEmpty(linkType) && !TextUtils.isEmpty(linkAddress)) {
            if ("2".equals(linkType)) {
                Intent intent = new Intent(context, CourseDetailActivity.class);
                intent.putExtra(BUNDLE_COURSE_ID, linkAddress);
                intent.putExtra("is_from_planet", true);
                context.startActivity(intent);
            } else if ("3".equals(linkType)) {
                if ("1".equals(linkAddress)) {
                    Intent intent = new Intent(context, ShareActivity.class);
                    context.startActivity(intent);
                } else if ("2".equals(linkAddress)) {
                    VipUtil.toVipPage(context);
                }
            }
        }
    }
}
