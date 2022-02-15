package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/11/3 13:56
 * company: 普华集团
 * description: 描述
 */
public class ShareItemBean {

    private String title;
    private String content;
    private String imgUrl;
    private String url;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

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
}
