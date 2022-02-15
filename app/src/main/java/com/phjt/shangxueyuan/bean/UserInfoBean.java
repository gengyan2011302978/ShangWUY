package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/3/31
 * company: 普华集团
 * description:
 */
public class UserInfoBean extends BaseBean {

    private String id;
    private Integer isTeacher;
    private String userName;
    private String nickName;
    private String mobile;
    private String password;
    private String photo;
    private int sex;
    private String regTime;
    private int loginTimes;
    private int freezeStatus;
    private String freezeChangedDate;
    private int ifBindingWx;
    private String openId;
    private String wxPhoto;
    private int vipState;
    private String vipStartTime;
    private String vipEndTime;
    private String lastloginIp;
    private String lastloginTime;
    private int isLogin;
    private String appVersion;
    private String updateTime;
    private int userPushCrmStatus;
    private int state;
    private String platform;

    public Integer getIsTeacher() {
        return isTeacher == null ? 0 : isTeacher;
    }

    public void setIsTeacher(Integer isTeacher) {
        this.isTeacher = isTeacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public int getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    public int getFreezeStatus() {
        return freezeStatus;
    }

    public void setFreezeStatus(int freezeStatus) {
        this.freezeStatus = freezeStatus;
    }

    public String getFreezeChangedDate() {
        return freezeChangedDate;
    }

    public void setFreezeChangedDate(String freezeChangedDate) {
        this.freezeChangedDate = freezeChangedDate;
    }

    public int getIfBindingWx() {
        return ifBindingWx;
    }

    public void setIfBindingWx(int ifBindingWx) {
        this.ifBindingWx = ifBindingWx;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxPhoto() {
        return wxPhoto;
    }

    public void setWxPhoto(String wxPhoto) {
        this.wxPhoto = wxPhoto;
    }

    public int getVipState() {
        return vipState;
    }

    public void setVipState(int vipState) {
        this.vipState = vipState;
    }

    public String getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(String vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public String getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public String getLastloginIp() {
        return lastloginIp;
    }

    public void setLastloginIp(String lastloginIp) {
        this.lastloginIp = lastloginIp;
    }

    public String getLastloginTime() {
        return lastloginTime;
    }

    public void setLastloginTime(String lastloginTime) {
        this.lastloginTime = lastloginTime;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserPushCrmStatus() {
        return userPushCrmStatus;
    }

    public void setUserPushCrmStatus(int userPushCrmStatus) {
        this.userPushCrmStatus = userPushCrmStatus;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
