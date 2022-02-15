package com.tencent.liteav.demo.play.bean;

/**
 * @author: gengyan
 * date:    2020/6/2 14:26
 * company: 普华集团
 * description: 描述
 */
public class TCMultipleBean {

    private float speedLevel;
    private boolean isSelect;

    public TCMultipleBean(float speedLevel) {
        this.speedLevel = speedLevel;
    }

    public TCMultipleBean(float speedLevel, boolean isSelect) {
        this.speedLevel = speedLevel;
        this.isSelect = isSelect;
    }

    public float getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(float speedLevel) {
        this.speedLevel = speedLevel;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
