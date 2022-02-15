package com.phjt.shangxueyuan.bean.event;


public class ChangeEvent {

    private int type;


    public ChangeEvent(int messageType) {
        this.type = messageType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
