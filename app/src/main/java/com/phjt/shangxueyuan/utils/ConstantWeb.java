package com.phjt.shangxueyuan.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phjt.base.http.errorhandler.handler.ErrorHandleSubscriber;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.WebAddressListBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phsxy.utils.SPUtils;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/11/5 10:36
 * company: 普华集团
 * description: H5地址
 */
public class ConstantWeb {

    /**
     * 保存h5地址列表
     */
    public static final String SP_WEB_URL_LIST = "sp_web_url_list";

    /**
     * 获取H5 详细地址
     *
     * @param type h5地址对应的type
     * @return h5地址
     */
    public static String getH5AddressById(int type) {
        String strWebList = SPUtils.getInstance().getString(SP_WEB_URL_LIST);
        List<WebAddressListBean> addressListBeans = new Gson().fromJson(strWebList, new TypeToken<List<WebAddressListBean>>() {
        }.getType());

        if (addressListBeans != null && !addressListBeans.isEmpty()) {
            for (int i = 0; i < addressListBeans.size(); i++) {
                WebAddressListBean webAddressListBean = addressListBeans.get(i);
                if (webAddressListBean != null && webAddressListBean.getType() == type) {
                    return webAddressListBean.getWebLink();
                }
            }
        }
        return "";
    }

    /**
     * 获取H5地址集合
     *
     * @param context
     */
    public static void getHttpWebAddressList(Context context) {
        CommonHttpManager.getInstance(context)
                .obtainRetrofitService(ApiService.class)
                .getWebAddressList()
                .compose(RxUtils.applySchedulers())
                .subscribe(new ErrorHandleSubscriber<BaseBean<List<WebAddressListBean>>>(CommonHttpManager.getHttpErrorHandler()) {
                    @Override
                    public void onNext(BaseBean<List<WebAddressListBean>> baseBean) {
                        if (baseBean.isOk()) {
                            List<WebAddressListBean> addressListBeans = baseBean.data;
                            SPUtils.getInstance().put(SP_WEB_URL_LIST, new Gson().toJson(addressListBeans));
                        }
                    }
                });
    }


    /**
     * 超级会员 phydstatic/vip(弃用)
     */
    public static final int H5_PHYDSTATIC_VIP = 1;

    /**
     * 下载APP phydstatic/downAPP
     */
    public static final int H5_PHYDSTATIC_DOWNAPP = 2;

    /**
     * 滑块 phydstatic/sliderVerification
     */
    public static final int H5_PHYDSTATIC_SLIDERVERIFICATION = 3;

    //---------------考试系统----------------
    /**
     * 手机号密码，手机号短信验证码登录 phydstatic/examSystem/login
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_LOGIN = 4;
    /**
     * 找回密码 phydstatic/examSystem/signin
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_SIGNIN = 5;
    /**
     * 主页 phydstatic/examSystem/home
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_HOME = 6;
    /**
     * 考试 phydstatic/examSystem/exam
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_EXAM = 7;
    /**
     * 报名考试 phydstatic/examSystem/enroll
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_ENROLL = 8;
    /**
     * 考生信息 phydstatic/examSystem/examInfo
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_EXAMINFO = 9;
    /**
     * 考生信息输入 phydstatic/examSystem/inputInfo
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_INPUTINFO = 10;
    /**
     * 成绩查询 phydstatic/examSystem/scoreQuery
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_SCOREQUERY = 11;
    /**
     * 报名科目 phydstatic/examSystem/subject
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_SUBJECT = 12;
    /**
     * 结算 phydstatic/examSystem/payment
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_PAYMENT = 13;
    /**
     * 结算状态 phydstatic/examSystem/payStatus
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_PAYSTATUS = 14;
    /**
     * 我的考试 phydstatic/examSystem/myExam
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_MYEXAM = 15;
    /**
     * 我的 phydstatic/examSystem/mine
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_MINE = 16;
    /**
     * 消息 phydstatic/examSystem/message/home
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_MESSAGE_HOME = 17;
    /**
     * 消息详情 phydstatic/examSystem/message/details
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_MESSAGE_DETAILS = 18;
    /**
     * 我的证书/我的订单 phydstatic/examSystem/other
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_OTHER = 19;
    /**
     * 证书详情 phydstatic/examSystem/certificateInfo
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_CERTIFICATEINFO = 20;
    /**
     * 注册协议 phydstatic/examSystem/agreement
     */
    public static final int H5_PHYDSTATIC_EXAMSYSTEM_AGREEMENT = 21;
    /**
     * 帮助中心 issue
     */
    public static final int H5_ISSUE = 22;
    /**
     * 超级会员 vip
     */
    public static final int H5_VIP = 23;
    /**
     * 翟老师的直播回放 livePlayback/home
     */
    public static final int H5_LIVEPLAYBACK_HOME = 24;
    /**
     * 登录-熵吾优 livePlayback/login
     */
    public static final int H5_LIVEPLAYBACK_LOGIN = 25;
    /**
     * 下载APP downAPP
     */
    public static final int H5_DOWNAPP = 26;
    /**
     * 注册协议 agreement
     */
    public static final int H5_AGREEMENT = 27;
    /**
     * 隐私政策 privacy
     */
    public static final int H5_PRIVACY = 28;
    /**
     * 权威证书 certificate/home
     */
    public static final int H5_CERTIFICATE_HOME = 29;
    /**
     * 资讯详情 certificate/details
     */
    public static final int H5_CERTIFICATE_DETAILS = 30;
    /**
     * 师资团队 certificate/teachers
     */
    public static final int H5_CERTIFICATE_TEACHERS = 31;
    /**
     * 创业基金 certificate/fund
     */
    public static final int H5_CERTIFICATE_FUND = 32;
    /**
     * 教学体系 certificate/teaching
     */
    public static final int H5_CERTIFICATE_TEACHING = 33;
    /**
     * 高薪择业 certificate/fatSalary
     */
    public static final int H5_CERTIFICATE_FATSALARY = 34;
    /**
     * 高薪择业 elite
     */
    public static final int H5_ELITE = 35;
    /**
     * 备考宝典 reference
     */
    public static final int H5_REFERENCE = 36;
    /**
     * 滑块 sliderVerification
     */
    public static final int H5_SLIDERVERIFICATION = 37;
    /**
     * 打卡训练营 activity
     */
    public static final int H5_ACTIVITY = 38;
    /**
     * 邀请分享 shareRules
     */
    public static final int H5_SHARERULES = 39;
    /**
     * 第三方打开分享 shareWX
     */
    public static final int H5_SHAREWX = 40;
    /**
     * 课程免费听活动页 freeCourseDrainage
     */
    public static final int H5_FREECOURSEDRAINAGE = 41;
    /**
     * 提现规则 withdrawalRules
     */
    public static final int H5_WITHDRAWALRULES = 42;
    /**
     * 专题详情 projectDetails
     */
    public static final int H5_PROJECTDETAILS = 43;
    /**
     * 评论详情页 phydstatic/livePlayback/dynamic
     */
    public static final int H5_DYNAMIC = 44;
    /**
     * 积分规则 integrationRule
     */
    public static final int H5_INTEGRATIONRULE = 45;
    /**
     * 新营销架构师
     */
    public static final int H5_CERTIFICATE_MARKETING = 46;
    /**
     * 信息化运营官
     */
    public static final int H5_CERTIFICATE_INFORMATION = 47;
    /**
     * BOC商科证书
     */
    public static final int H5_CERTIFICATE_BOC = 48;
    /**
     * 权威证书
     */
    public static final int H5_CERTIFICATE_NAV = 49;
    /**
     * 扫码支付
     */
    public static final int H5_SCAN_CODE = 50;
    /**
     * 日记详情
     */
    public static final int H5_DIARY_DETAILS = 52;
    /**
     * 打卡主页
     */
    public static final int H5_PUNCH_CLOCK = 53;
    /**
     * 学豆兑换BOCC: http://example.com/integral?type=1
     * 优币兑换学豆: http://example.com/integral?type=2
     */
    public static final int H5_CURRENCY_EXCHANG_BOCC = 56;
    /**
     * 商城 -我的学豆页面-兑换规则  mall/rules
     */
    public static final int H5_MALL_EXCHANGE_RULE = 57;
    /**
     * 商城 -课程详情页  mall/detail?id=课程id
     */
    public static final int H5_MALL_COMMODITY_DETAIL = 58;

    /**
     * 启富通首页
      */
    public static final int H5_QIITONG_HOME = 59;
    /**
     * 开通专栏
     */
    public static final int H5_OPEN_COLUMN = 60;
}
