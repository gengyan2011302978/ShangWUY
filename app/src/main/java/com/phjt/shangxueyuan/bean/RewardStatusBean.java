package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public class RewardStatusBean implements Serializable {
    private String imageVal;
    private String imageShow;

    public String getImageVal() {
        return imageVal;
    }

    public void setImageVal(String imageVal) {
        this.imageVal = imageVal;
    }

    public String getImageShow() {
        return imageShow;
    }

    public void setImageShow(String imageShow) {
        this.imageShow = imageShow;
    }
}
