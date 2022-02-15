package com.phjt.shangxueyuan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.phjt.shangxueyuan.app.MyApplication;
import com.phsxy.utils.SPUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Umeng util.
 *
 * @author yanmengjun
 */
public class UmengUtil {
    /**
     * event id：自定义事件id。
     * 行为事件
     * 轮播屏
     * 权威证书、考试体系、高薪择业、创业基金栏目按钮
     * 精品试听、BOC考证通道、BOC创富通道按钮
     * 导航栏：试听、报名、精英、我的
     */
    public static final String EVENT_ID_MAIN = "main";
    public static final String EVENT_ZXKF_MAIN = "zxkf_main";
    public static final String EVENT_ZNXX_MAIN = "znxx_main";
    public static final String EVENT_DBLBT_MAIN = "dblbt_main";
    public static final String EVENT_CIRCLELBT_MAIN = "circlelbt_main";
    public static final String EVENT_QWZS_MAIN = "qwzs_main";
    public static final String EVENT_JPST_MAIN = "jpst_main";
    public static final String EVENT_BKBD_MAIN = "bkbd_main";
    public static final String EVENT_VIP_MAIN = "vip_main";
    public static final String EVENT_CJKC_MAIN = "cjkc_main";
    public static final String EVENT_ZJKC_MAIN = "zjkc_main";
    public static final String EVENT_GJKC_MAIN = "gjkc_main";
    public static final String EVENT_CFTD_MAIN = "cftd_main";
    public static final String EVENT_GMDT_MAIN = "gmdt_main";
    public static final String EVENT_ZBBANNER_MAIN = "zbbanner_main";
    public static final String EVENT_BOCSKZS_MAIN = "bocskzs_main";
    public static final String EVENT_BOCSKZSBT_MAIN = "bocskzsbt_main";
    public static final String EVENT_JPHKBT_MAIN = "jphkbt_main ";
    public static final String EVENT_JPHK1_MAIN = "jphk1_main";
    public static final String EVENT_JPHK2_MAIN = "jphk2_main";
    public static final String EVENT_JPHK3_MAIN = "jphk3_main";
    public static final String EVENT_JPHK4_MAIN = "jphk4_main";
    public static final String EVENT_HOTZXBT_MAIN = "hotzxbt_main";
    public static final String EVENT_RMZX1_MAIN = "rmzx1_main";
    public static final String EVENT_RMZX2_MAIN = "rmzx2_main";
    public static final String EVENT_RMZX3_MAIN = "rmzx3_main";
    public static final String EVENT_SCGK_MAIN = "scgk_main";
    public static final String EVENT_SCGKGB_MAIN = "scgkgb_main";
    public static final String EVENT_DBSY_MAIN = "dbsy_main";
    public static final String EVENT_DBXX_MAIN = "dbxx_main";
    public static final String EVENT_DBJY_MAIN = "dbjy_main";
    public static final String EVENT_DBQZ_MAIN = "dbqz_main";
    public static final String EVENT_DBWD_MAIN = "dbwd_main";
    public static final String EVENT_DBMALL_MAIN = "dbmall_main";
    public static final String EVENT_JYKFAN_MAIN = "jykfan_main";
    public static final String EVENT_JYZNTZAN_MAIN = "jyzntzan_main";
    public static final String EVENT_BG_LOGIN = "login_bg";
    public static final String EVENT_BUTTON_LOGIN = "login_button";
    public static final String EVENT_WX_LOGIN = "login_wx";
    public static final String EVENT_PASSWORD_LOGIN = "login_password";
    public static final String EVENT_FORGET_LOGIN = "login_forget";
    public static final String EVENT_USER_DOC = "login_userdoc";
    /**
     * 超级会员页	被打开
     */
    public static final String EVENT_ID_VIP = "vip";
    /**
     * 课程详情页	领取资料、开通VIP会员按钮被点击
     * 权威证书页	领取资料、开通VIP会员按钮被点击
     * 考试体系页	领取资料、开通VIP会员按钮被点击
     * 高薪择业页	领取资料、开通VIP会员按钮被点击
     * 创业基金页	领取资料、开通VIP会员按钮被点击
     */
    public static final String EVENT_ID_OPEN_VIP = "open_vip";
    /**
     * 权威证书	被打开
     * 考试体系	被打开
     * 高薪择业	被打开
     * 创业基金	被打开
     * 精品试听	被打开
     * BOC考证通道	被打开
     * BOC创富通道	被打开
     * 精英	被打开
     */
    public static final String EVENT_ID_WEB = "web";

    /**
     * 超级会员页:	月卡、年卡、终身卡各自被点击
     */
    public static final String EVENT_ID_PAY = "pay";

    /**
     * 支付页:
     * main_vip_pay_yk,支付页月卡,0
     * main_vip_pay_nk,支付页年卡,0
     * main_vip_pay_zsk,支付页终身卡,0
     * <p>
     * pay_wechat:微信支付
     * pay_ali:支付宝支付
     */

    public static final String EVENT_ID_PAY_YK = "pay_yk";
    public static final String EVENT_ID_PAY_NK = "pay_nk";
    public static final String EVENT_ID_PAY_ZSK = "pay_zsk";
    public static final String EVENT_ID_PAY_WECHAT = "pay_wechat";
    public static final String EVENT_ID_PAY_ALI = "pay_ali";
    public static final String EVENT_ID_PAY_CANCEL = "pay_cancel";

/*
    kcxq_page,课程详情,0
    qwzs_page,权威证书,0
  */

    public static final String EVENT_ID_PAGE_KCXQ = "kcxq_page";
    public static final String EVENT_ID_PAGE_QWZS = "qwzs_page";


    /**
     * 注册页
     *
     * @param context context
     * @param name    name
     */
    public static void onEventLoginPage(Context context, String name, String keyclick) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));

        MobclickAgent.onEventObject(context, keyclick, map);
    }


    public static void umengCount(Activity activity, String eventId) {
        umengCount(activity, eventId, null);
    }

    /**
     * 友盟事件统计
     */
    public static void umengCount(Activity activity, String eventId, String name) {
        if (activity != null) {
            String phone = SPUtils.getInstance().getString(Constant.SP_MOBILE);
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("phone", phone);
            if (!TextUtils.isEmpty(name)) {
                map.put("name", name);
            }
            MobclickAgent.onEvent(activity, eventId, map);
        }
    }

    /**
     * 首页统计
     *
     * @param context context
     * @param name    name
     */
    public static void onEventMainPage(Context context, String name, String mainKey) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));

        MobclickAgent.onEventObject(context, mainKey, map);
    }

    public static void onEventMainPage(String name, String mainKey) {
        onEventMainPage(MyApplication.instance(), name, mainKey);
    }

    /**
     * 会员页:
     *
     * @param context context
     * @param name    name
     */
    public static void onEventVipPage(Context context, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));

        MobclickAgent.onEventObject(context, EVENT_ID_VIP, map);
    }

    /**
     * 开通会员/领取资料
     *
     * @param context context
     * @param name    name
     */
    public static void onEventOpenVipPage(Context context, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", "开通VIP会员");
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));

        MobclickAgent.onEventObject(context, EVENT_ID_OPEN_VIP, map);
    }

    /**
     * 开通会员/领取资料：
     * 课程详情页|领取资料、开通VIP会员按钮被点击
     *
     * @param context  context
     * @param name     name
     * @param typeName typeName：领取资料|开通VIP会员
     */
    public static void onEventOpenVipPage(Context context, String name, String typeName) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("type", typeName);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));

        //kcxq_page,课程详情,0   qwzs_page,权威证书,0

        if (name.contains("课程详情")) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAGE_KCXQ, map);

        } else if (name.contains("权威证书")) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAGE_QWZS, map);
        }
    }

    /**
     * 网页：  权威证书|被打开
     *
     * @param context context
     * @param name    name
     */
    public static void onEventWebPage(Context context, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));


        MobclickAgent.onEventObject(context, EVENT_ID_WEB, map);
    }

    /**
     * 支付页面：
     * 月卡拉起的支付页面
     * 年卡拉起的支付页面
     * 终身卡拉起的支付页面
     *
     * @param context  context
     * @param cardType cardType 月卡，年卡，终身卡
     * @param mPayType 1支付宝 2微信
     */
    public static void onEventPayPage(Context context, String cardType, int mPayType) {
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));
        map.put("cardType", cardType);
        map.put("payType", mPayType == 1 ? "支付宝" : "微信");

        if (mPayType == 1) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAY_ALI, map);
        } else if (mPayType == 2) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAY_WECHAT, map);
        }

        if (cardType.contains("月卡")) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAY_YK, map);
        } else if (cardType.contains("年卡")) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAY_NK, map);
        } else if (cardType.contains("终身卡")) {
            MobclickAgent.onEventObject(context, EVENT_ID_PAY_ZSK, map);
        }
    }

    /**
     * 友盟统计事件
     *
     * @param context 上下文
     * @param key     友盟事件id
     * @param name    事件名称
     */
    public static void onUmengUtils(Context context, String key, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("mobile", SPUtils.getInstance().getString(Constant.SP_MOBILE));
        MobclickAgent.onEventObject(context, key, map);
    }

    /**
     * 课程模块 ：精品试听、初级、中级、高级课程分类 和 最新、最热、讲师
     */
    public static final String COURSE_CATEGORY_AUDITION = "category_audition";
    public static final String COURSE_CATEGORY_AUDITION_TYPE = "category_audition_type";
    public static final String COURSE_CATEGORY_PRIMARY = "category_primary";
    public static final String COURSE_CATEGORY_PRIMARY_TYPE = "category_primary_type";
    public static final String COURSE_CATEGORY_MIDDLE = "category_middle";
    public static final String COURSE_CATEGORY_MIDDLE_TYPE = "category_middle_type";
    public static final String COURSE_CATEGORY_HIGH = "category_high";
    public static final String COURSE_CATEGORY_HIGH_TYPE = "category_high_type";
    public static final String COURSE_CATEGORY_RICH = "category_rich";
    public static final String COURSE_CATEGORY_RICH_TYPE = "category_rich_type";
    //分享
    public static final String SHARE = "share";
    /**
     * web h5统计使用: 权威证书  备考宝典  vip会员
     */
    public static final String WEB_AUTHORITY_CERTIFICATE = "web_authority_certificate";
    public static final String WEB_PREPARING_EXAMS = "web_preparing_exams";
    public static final String WEB_VIP_PAGE = "web_vip_page";

    /**
     * 获取友盟渠道
     *
     * @param context
     * @param name
     * @return
     */
    public static String getAppInfo(Context context, String name) {
        String value = "phjt";
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = info.metaData.getString(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

}
