package com.phjt.shangxueyuan.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author: Roy
 * date:   2020/5/8
 * company: 普华集团
 * description:
 */
public class DescBean {

    public String boldText;
    public String ordinaryText;

    public String getBoldText() {
        return boldText;
    }

    public void setBoldText(String boldText) {
        this.boldText = boldText;
    }

    public String getOrdinaryText() {
        return ordinaryText;
    }

    public void setOrdinaryText(String ordinaryText) {
        this.ordinaryText = ordinaryText;
    }
}
