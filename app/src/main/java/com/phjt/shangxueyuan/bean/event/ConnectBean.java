package com.phjt.shangxueyuan.bean.event;

/**
 * @author: gengyan
 * date:    2020/5/12 13:01
 * company: 普华集团
 * description: 描述
 */
public class ConnectBean {

    private boolean isConnect = false;

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public ConnectBean(boolean isConnected) {
        this.isConnect = isConnected;
    }
}
