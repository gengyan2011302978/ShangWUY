package com.phjt.shangxueyuan.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author : Arjun
 * description :TokenBean
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2019/11/12 19:06
 */
public class TokenBean implements Serializable {
    @SerializedName("token")
    public String token;
    @SerializedName("photoUrl")
    public String photoUrl;
    @SerializedName("userName")
    public String userName;
    @SerializedName("phone")
    public String phone;
    //用户信息是否完善（0未完善，1已完善）
    @SerializedName("userInfoIsPerfect")
    public int userInfoPerfect;
    //是否有弟子证 :0-未获得弟子证， 1-已获得弟子证
    public int userHaveDiscipleNo;
    //用户是否已认证
    public int userCerStatus;
    //0未领取 1领取了
    public int ifReceive;

}
