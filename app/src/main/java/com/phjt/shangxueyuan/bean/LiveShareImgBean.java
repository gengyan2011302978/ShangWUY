package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: Roy
 * date:   2021/4/14
 * company: 普华集团
 * description:
 */
public class LiveShareImgBean  implements Serializable {
    String tile;
    String inviteImg;
    String url;

    public String getTitle() {
        return tile;
    }

    public void setTitle(String title) {
        this.tile = title;
    }

    public String getInviteImg() {
        return inviteImg;
    }

    public void setInviteImg(String inviteImg) {
        this.inviteImg = inviteImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
