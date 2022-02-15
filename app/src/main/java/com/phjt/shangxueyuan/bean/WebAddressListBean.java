package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/11/5 14:37
 * company: 普华集团
 * description: 描述
 */
public class WebAddressListBean {

    private int id;
    private String webName;
    private String webLink;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }
}
