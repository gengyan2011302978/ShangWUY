package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/4/3 14:29
 * company: 普华集团
 * description: 描述
 */
public class VipStateBean {

    /**
     * 0.普通用户；1.普通vip；2.永久vip；3.vip已过期
     */

    private Integer vipState;

    public Integer getVipState() {
        return vipState == null ? 0 : vipState;
    }

    public void setVipState(Integer vipState) {
        this.vipState = vipState;
    }
}
