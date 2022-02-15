package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/2/1
 * company: 普华集团
 * description:
 */
public class SaveGeneratePicturesBean {
    private String id;
    private String photo;
    private String nickName;
    private String punchCardName;
    private String backgroundImg;
    private String punchCardCode;
    private String partyNum;
    private String perCentum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPunchCardName() {
        return punchCardName;
    }

    public void setPunchCardName(String punchCardName) {
        this.punchCardName = punchCardName;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public String getPunchCardCode() {
        return punchCardCode;
    }

    public void setPunchCardCode(String punchCardCode) {
        this.punchCardCode = punchCardCode;
    }

    public String getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(String partyNum) {
        this.partyNum = partyNum;
    }

    public String getPerCentum() {
        return perCentum;
    }

    public void setPerCentum(String perCentum) {
        this.perCentum = perCentum;
    }
}
