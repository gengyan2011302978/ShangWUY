package com.phjt.shangxueyuan.bean.event;


public class ChangeMainItemEvent {
    private int index;

    public ChangeMainItemEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
