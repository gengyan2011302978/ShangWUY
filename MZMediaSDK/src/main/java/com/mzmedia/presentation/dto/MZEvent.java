package com.mzmedia.presentation.dto;

/**
 * @author: gengyan
 * date:    2021/4/9 16:30
 * company: 普华集团
 * description: 描述
 */
public class MZEvent {

    /**
     * 获取直播简介
     */
    public static final int TYPE_GET_LIVE_DES = 100;
    /**
     * 传递直播简介
     */
    public static final int TYPE_LOAD_LIVE_DES = 101;
    /**
     * 赠送礼物
     */
    public static final int TYPE_SEND_GIF = 102;
    /**
     * 赠送礼物成功
     */
    public static final int TYPE_SEND_GIF_SUCCESS = 103;
    /**
     * 传递封面图
     */
    public static final int TYPE_LOAD_IMG_BG = 104;
    /**
     * 充值页面，关闭礼物dialog
     */
    public static final int TYPE_CLOSE_GIF_DIALOG = 105;
    /**
     * 发送消息
     */
    public static final int TYPE_SEND_MSG_SUCCESS = 106;
    /**
     * 加入黑名单，被踢出直播间
     */
    public static final int TYPE_KICK_OUT = 107;

    public int type;
    public String ticketId;
    public String liveId;
    public String liveDes;
    public Double integral;
    public String gifName;

    /**
     * 获取直播简介
     */
    public MZEvent(int type, String liveId) {
        this.type = type;
        this.liveId = liveId;
    }

    /**
     * 传递直播简介
     */
    public MZEvent(int type, String ticketId, String liveDes, Double integral) {
        this.type = type;
        this.ticketId = ticketId;
        this.liveDes = liveDes;
        this.integral = integral;
    }

    /**
     * 赠送礼物
     */
    public MZEvent(int type, String liveId, Double integral, String gifName) {
        this.type = type;
        this.liveId = liveId;
        this.integral = integral;
        this.gifName = gifName;
    }

    public MZEvent(int type) {
        this.type = type;
    }
}
