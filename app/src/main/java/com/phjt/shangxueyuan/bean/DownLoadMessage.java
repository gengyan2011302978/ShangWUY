package com.phjt.shangxueyuan.bean;

/**
 * @author : austenYang
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date :  2019/10/30 15:27
 */
public class DownLoadMessage {
    public static final int DOWN_FINISH = 1;
    public static final int DOWN_REFRESH = 2;
    public static final int DOWN_STOP = 3;
    public static final int DOWN_DELETE = 4;

    private int type;

    public DownLoadMessage(int type) {
        this.type = type;
    }

    public int getMessageType() {
        return type;
    }
}
