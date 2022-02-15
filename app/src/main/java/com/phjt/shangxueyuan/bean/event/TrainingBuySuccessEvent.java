package com.phjt.shangxueyuan.bean.event;

/**
 * @author: gengyan
 * date:    2021/2/2 16:54
 * company: 普华集团
 * description: 训练营报名成功和购买成功
 */
public class TrainingBuySuccessEvent {

    /**
     * 1：免费  2：付费
     */
    private int type;

    public TrainingBuySuccessEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
