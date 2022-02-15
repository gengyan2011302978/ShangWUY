package com.phjt.shangxueyuan.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.okdownload.DownloadTask;
import com.lzf.easyfloat.EasyFloat;
import com.phjt.base.base.BaseApplication;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.greendao.gen.DaoMaster;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.utils.crash.SpiderMan;
import com.phsxy.utils.LogUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author: lyx
 * date: 2019/11/29 10:52
 * company: 普华集团
 * description: 描述
 */
public class MyApplication extends BaseApplication {

    private static MyApplication instance;

    public static MyApplication instance() {
        return instance;
    }

    public static HashMap<String, DownloadTask> downloadTaskHashMap = new HashMap<String, DownloadTask>();
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private FileDao fileDao;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (!MyBusiness.isProductRelease()) {
            //不是生成环境，进行异常初始化
            SpiderMan.init(this);
        }
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, BuildConfig.BUGLY_APPID, BuildConfig.IS_DEBUG, strategy);

        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);

        //TbsReaderView初始化
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtils.e("=====onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.e("=====X5InitFinished" + b);
            }
        });

        EasyFloat.init(this, BuildConfig.IS_DEBUG);
    }

    /**
     * 设置greenDao
     */
    public void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(instance, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
