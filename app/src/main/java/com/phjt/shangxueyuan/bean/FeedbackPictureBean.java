package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: Created by shaopengfei
 * date: 2020/5/8 13:46
 * company: 普华集团
 * description: 意见反馈
 */
public class FeedbackPictureBean implements Serializable {
    private String urlPath;
    private String absolutePath;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
