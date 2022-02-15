package com.phjt.shangxueyuan.app;

import android.app.Notification;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.phjt.shangxueyuan.BuildConfig;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.sharestatistic.ShareInit;
import com.phjt.sharestatistic.inter.IPlatKeySecretConfig;
import com.phsxy.utils.LogUtils;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * @author: gengyan
 * date:    2021/6/4 18:07
 * company: 普华集团
 * description: 友盟初始化
 */
public class UMInitHelper {

    private static final String TAG = UMInitHelper.class.getSimpleName();

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     *
     * @param context 应用上下文
     */
    public static void preInit(Context context) {

        String channel = UmengUtil.getAppInfo(context, Constant.UMENG_CHANNEL);
        if (TextUtils.isEmpty(channel)) {
            channel = "phjt";
        }

        try {
            //解决推送消息显示乱码的问题
            AccsClientConfig.Builder builder = new AccsClientConfig.Builder();
            builder.setAppKey("umeng:" + BuildConfig.UMENG_KEY);
            builder.setAppSecret(BuildConfig.UMENG_SECRET);
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG);
            ACCSClient.init(context, builder.build());
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UMConfigure.preInit(context, BuildConfig.UMENG_KEY, channel);
        if (!isMainProcess(context)) {
            init(context);
        }
    }

    /**
     * 初始化。
     * 场景：用户已同意隐私政策协议授权时
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        String channel = UmengUtil.getAppInfo(context, Constant.UMENG_CHANNEL);
        if (TextUtils.isEmpty(channel)) {
            channel = "phjt";
        }
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息
        UMConfigure.init(context, BuildConfig.UMENG_KEY, channel, UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.UMENG_SECRET);
        PlatformConfig.setWeixin(Constant.WECHAT_APPID, Constant.WECHAT_SECRET);
        // 选用MANUAL页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL);

        //获取消息推送实例
        final PushAgent pushAgent = PushAgent.getInstance(context);

        pushAdvancedFunction(context);

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String s) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtils.e("====友盟推送注册成功：deviceToken：-------->" + s);
            }

            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("====友盟推送注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        //集成分享组件，因为推送已经初始化过友盟，所以下述无需重新初始化
        ShareInit.getInstance()
                .with(context)
                .config(new IPlatKeySecretConfig() {
                    @Override
                    public void configQQ() {
                    }

                    @Override
                    public void configWechat() {
                        PlatformConfig.setWeixin(Constant.WECHAT_APPID, Constant.WECHAT_SECRET);
                        PlatformConfig.setWXFileProvider(BuildConfig.APPLICATION_ID + ".fileprovider");
                    }

                    @Override
                    public void configWeibo() {

                    }

                    @Override
                    public void initSDKWithChannel() {

                    }
                })
                .init();
    }

    /**
     * 是否运行在主进程
     *
     * @param context 应用上下文
     * @return true: 主进程；false: 子进程
     */
    public static boolean isMainProcess(Context context) {
        return UMUtils.isMainProgress(context);
    }


    //推送高级功能集成说明
    private static void pushAdvancedFunction(Context context) {
        PushAgent pushAgent = PushAgent.getInstance(context);

        //设置通知栏显示通知的最大个数（0～10），0：不限制个数
        pushAgent.setDisplayNotificationNumber(8);

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengMessageHandler messageHandler = new UmengMessageHandler() {

            @Override
            public Notification getNotification(Context context, UMessage msg) {
                LogUtils.e("=============msg.text:" + msg.text + "=============msg.title:" + msg.title);
                try {
                    if (msg.extra != null) {
                        for (Map.Entry<String, String> entry : msg.extra.entrySet()) {
                            String value = entry.getValue();
                            String key = entry.getKey();
                            LogUtils.e("=============entry.key:" + key + "=============entry.value:" + value);
                            if (key.contains("couponSend")) {
                                EventBus.getDefault().post(new EventBean(EventBean.PROPELLING_MOVEMENT, "" + key, "" + value, msg.title, msg.text));
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.getNotification(context, msg);
            }
        };
        pushAgent.setMessageHandler(messageHandler);

        //推送消息点击处理
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
                LogUtils.e("=============launchApp" + uMessage.extra.toString());
                try {
                    if (uMessage.extra != null) {
                        for (Map.Entry<String, String> entry : uMessage.extra.entrySet()) {
                            String value = entry.getValue();
                            String key = entry.getKey();
                            LogUtils.e("=============entry.key:" + key + "=============entry.value:" + value);
                            if (key.contains("vip")) {
                                EventBus.getDefault().post(new EventBean(EventBean.PUSH_VIP, ""));
                            } else if (key.contains("exam")) {
                                EventBus.getDefault().post(new EventBean(EventBean.PUSH_EXAM, ""));
                            } else if (key.contains("MESSAGE")) {
                                EventBus.getDefault().post(new EventBean(EventBean.PUSH_MESSAGE, "" + value));
                            } else if (key.contains("trainingCampSend")) {
                                EventBus.getDefault().post(new EventBean(EventBean.TRAINING_CAMP_SEND, "" + value));
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void openActivity(Context context, UMessage uMessage) {
                super.openActivity(context, uMessage);
                LogUtils.e("=============openActivity" + uMessage.activity + "==extra::" + uMessage.extra.toString());
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                LogUtils.e("=============dealWithCustomAction" + uMessage.custom);
            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);
    }
}
