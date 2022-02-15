package com.phjt.shangxueyuan.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class ScreenlBean extends BaseBean {

    private String id;
    private String realmName;
    public boolean isCheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
