package com.phjt.shangxueyuan.bean.event;


public class ChangeInfoEvent {

    private int type;


    public ChangeInfoEvent(int messageType) {
        this.type = messageType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
