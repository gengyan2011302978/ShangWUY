package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/6/25
 * company: 普华集团
 * description:
 */
public class DisabuseBean {
    private String name;
    private int id;
    public boolean isCheck;
    public DisabuseBean(String name, int id,boolean isChecks) {
        this.name = name;
        this.id = id;
        this.isCheck = isChecks;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
