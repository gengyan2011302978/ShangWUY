package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public class ScreenLabelBean {
    private int id;
    private String realmName;
    private boolean isCheck;

    public ScreenLabelBean(int ids, String realmNames, boolean isChecks) {
        this.id = ids;
        this.realmName = realmNames;
        this.isCheck = isChecks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
