package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2019/11/15 18:28
 * company: 普华集团
 * description: 分享的实体
 */
public class ShareBean implements Serializable {

    private String title;
    private String content;
    private String imgUrl;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShareBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
