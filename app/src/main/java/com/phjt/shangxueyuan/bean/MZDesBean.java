package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2021/4/13 13:54
 * company: 普华集团
 * description: 盟主直播简介
 */
public class MZDesBean {

    private String ticketId;
    private Double integral;
    private String introduction;
    private Integer permission;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getIntegral() {
        return integral == null ? 0 : integral;
    }

    public void setIntegral(Double integral) {
        this.integral = integral;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPermission() {
        return permission == null ? 0 : permission;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }
}
