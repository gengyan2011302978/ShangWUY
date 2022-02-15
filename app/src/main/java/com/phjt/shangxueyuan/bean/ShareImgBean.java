package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

/**
 * @author: gengyan
 * date:    2020/8/11 9:24
 * company: 普华集团
 * description: 收入记录实体
 */
public class ShareImgBean implements Serializable {


    /**
     * id : 1
     * inviteTitle : 111
     * inviteImg : JPG
     * sort : 1
     */

    private int id;
    private String inviteTitle;
    private String inviteImg;
    private String sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInviteTitle() {
        return inviteTitle;
    }

    public void setInviteTitle(String inviteTitle) {
        this.inviteTitle = inviteTitle;
    }

    public String getInviteImg() {
        return inviteImg;
    }

    public void setInviteImg(String inviteImg) {
        this.inviteImg = inviteImg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
