package com.phjt.shangxueyuan.app;

import android.app.Application;
import android.content.Context;

import com.mengzhu.live.sdk.core.MZSDKInitManager;
import com.mzmedia.utils.MZLogUtils;
import com.phjt.base.base.delegate.AppLifecycles;
import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.common.MyBusiness;
import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.AppContextUtil;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.PushAgent;

import androidx.annotation.NonNull;
import me.jessyan.autosize.AutoSizeConfig;

/**
 * @author : austenYang
 * company    : 普华
 * date       : 2019/3/27 17:51
 * description: {@link Application} 的生命周期代理类
 * 代理类是通过在{@link GlobalConfiguration}添加的框架中
 * 在使用时可以根据业务需求创建多个，尤其是在组件化应用中
 * 每个 module 中可以配置一个
 */
public class AppLifecyclesImpl implements AppLifecycles {

    private static PushAgent mPushAgent;

    public static PushAgent getPushAgent() {
        return mPushAgent;
    }

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {
        //工具类初始化
        AppContextUtil.init(application);
        //日志开关处理
        LogUtils.getConfig().setLogSwitch(!MyBusiness.isProductRelease());
        //初始化业务层图片加载逻辑
        AppImageLoader.init(application);
        //屏幕适配
        initAutoSize();
        initSmartRefreshLayout();
        //盟主直播   运行环境：true为debug false为生产环境
        MZSDKInitManager.getInstance().initApplication(application, BuildConfig.MZ_APP_ID, false);
        MZLogUtils.setMZLogDebug(!MyBusiness.isProductRelease());
        //友盟初始化
        umengPreInit(application);
    }

    private void umengPreInit(Application application) {
        mPushAgent =  PushAgent.getInstance(application);
        //日志开关
        UMConfigure.setLogEnabled(BuildConfig.IS_DEBUG);
        //预初始化
        UMInitHelper.preInit(application);
        //正式初始化
        umengFormalInit(application);
    }

    private void umengFormalInit(Application application) {
        /*
         * 判断用户是否已同意隐私政策
         * 当同意时，直接进行初始化；
         * 当未同意时，待用户同意后，通过UMInitHelper.init(...)方法进行初始化。
         */
        boolean isAgree = SPUtils.getInstance().getBoolean(Constant.AGREEMENT, false);
        if (isAgree && UMInitHelper.isMainProcess(application)) {
            new Thread(() -> UMInitHelper.init(application)).start();
        }
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    private void initSmartRefreshLayout() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(android.R.color.transparent, android.R.color.white);
            //指定为经典Header，默认是 贝塞尔雷达Header
            return new ClassicsHeader(context).setAccentColor(context.getResources().getColor(R.color.color_black));
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(android.R.color.transparent, android.R.color.white);
            return new ClassicsFooter(context).setAccentColor(context.getResources().getColor(R.color.color_black));
        });
    }

    private void initAutoSize() {
        AutoSizeConfig.getInstance()
                //是否全局按照宽度进行等比例适配
                .setBaseOnWidth(true)
                //为使用设备的实际尺寸 (包含状态栏)
                .setUseDeviceSize(true)
                //App 内的字体大小不随系统设置中字体大小而改变.
                .setExcludeFontScale(true)
                //日志控制
                .setLog(!MyBusiness.isProductRelease())
                //开启 AutoSize 对Fragment的支持
                .setCustomFragment(true);
    }
}
