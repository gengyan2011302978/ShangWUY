package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2019/11/1 13:40
 * company: 普华集团
 * description: 基类
 */
public class BaseBean<T> implements Serializable {

    public int code;
    public String msg;
    public T data;
    public String encodeStr;

    public boolean isOk() {
        return code == 0;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", encodeStr='" + encodeStr + '\'' +
                '}';
    }
}
