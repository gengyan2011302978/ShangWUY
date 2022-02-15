package com.phjt.shangxueyuan.bean.event;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author : gengyan
 * time    : 2019/4/18
 * desc    : EventBus传递的实体
 */
public class EventBean implements Serializable {
    /**
     * 公开笔记添加
     */
    public static final int NOTES_REVIEW_PUBLIC = 100;
    /**
     * 笔记回复成功
     */
    public static final int NOTES_REVIEW_SUCCESSFUL = 101;
    /**
     * 创建笔记
     */
    public static final int NOTES_FOUND = 102;
    /**
     * 笔记回复
     */
    public static final int NOTES_REVIEW = 103;

    //支付成功-关闭订单详情页
    public static final int PAY_SUCCESS = 104;

    //支付成功-关闭订单详情页
    public static final int PAY_FAILED = 105;
    /**
     * 日记发布/编辑
     */
    public static final int ADD_DIARY = 106;

    /**
     * 取消收藏
     */
    public static final int CANCEL_COLLECTIONS = 107;
    /**
     * 推送考试系统
     */
    public static final int PUSH_EXAM = 108;
    /**
     * 推送vip
     */
    public static final int PUSH_VIP = 109;

    /**
     * 课程详情页
     */
    public static final int CORRESPONDENT_COURSE = 110;

    /**
     * 推送MESSAGE
     */
    public static final int PUSH_MESSAGE = 113;

    /**
     * 任务去首页
     */
    public static final int PUSH_HOME = 114;
    /**
     * 任务去首页——圈子
     */
    public static final int PUSH_CIRCLE = 115;


    /**
     * 推送横幅
     */
    public static final int PROPELLING_MOVEMENT = 116;

    /**
     * 推送训练营
     */
    public static final int TRAINING_CAMP_SEND = 117;

    /**
     * 分享图片
     */
    public static final int LIVE_BITMAP = 118;


    /**
     * 关闭设置页
     */
    public static final int CLOSE_DOWN_SET = 119;
    /**
     *  筛选状态
     */
    public static final int SCREENING_STATUS = 120;
    /**
     * 擅长领域-导师
     */
    public static final int REALM_ID = 121;
    /**
     * 擅长领域-问答
     */
    public static final int QUESTIONS_ID = 122;
    /**
     * 擅长领域-咨询
     */
    public static final int MY_ANSWERS_ID = 123;
    /**
     * 提问支付
     */
    public static final int ANSWERS_PAY = 124;

    /**
     * type: 传递类型
     * msg : 传递内容
     */
    private int type;
    private String msg;
    private String title;
    private String content;
    private String key;
    private int pass;
    private int payType;
    private int quantity;
    private Bitmap bitmap;

    public EventBean(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
    public EventBean(int type, int payTypes,int quantitys) {
        this.type = type;
        this.payType = payTypes;
        this.quantity = quantitys;
    }


    public EventBean(int type, String key, String msg, String title, String content) {
        this.type = type;
        this.key = key;
        this.msg = msg;
        this.content = content;
        this.title = title;

    }

    public EventBean(int type, Bitmap bitmaps, String msg) {
        this.type = type;
        this.bitmap = bitmaps;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
