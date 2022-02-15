package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/1/30
 * company: 普华集团
 * description:
 */
public class IntroductionTopCardsBean {

    private String id;
    private String punchCardName;
    private int partyNum;
    private int partyUser;
    private String helperName;
    private String photo;
    private String wxNumber;
    private String couId;
    private List<UserVos> userVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPunchCardName() {
        return punchCardName;
    }

    public void setPunchCardName(String punchCardName) {
        this.punchCardName = punchCardName;
    }

    public int getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(int partyNum) {
        this.partyNum = partyNum;
    }

    public int getPartyUser() {
        return partyUser;
    }

    public void setPartyUser(int partyUser) {
        this.partyUser = partyUser;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public String getCouId() {
        return couId;
    }

    public void setCouId(String couId) {
        this.couId = couId;
    }

    public List<UserVos> getUserVos() {
        return userVos;
    }

    public void setUserVos(List<UserVos> userVos) {
        this.userVos = userVos;
    }

    public static class UserVos {
        private String photo;

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
}
