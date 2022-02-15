package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class UpdateAppBean implements Serializable {

    /**
     * id : 7
     * versionCode : 45
     * versionName : 2.7.0
     * systemType : 1
     * versionUrl : /static/app/caifuwajueji_v2.6.0_20191127_beta_1.apk
     * upgradeType : 2
     * description : 1、社区正式升级为精英圈子
     2、精英圈子内可新建您的兴趣群组
     3、精英圈子内可加入您喜欢的群组
     4、圈子内可进行发帖互动
     5、修复已知bug
     * createTime : 2020-01-14T02:28:10.000+0000
     */

    private int id;
    private int versionCode;
    private String versionName;
    private int systemType;
    private String versionUrl;
    private int upgradeType;
    private String description;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getSystemType() {
        return systemType;
    }

    public void setSystemType(int systemType) {
        this.systemType = systemType;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public int getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(int upgradeType) {
        this.upgradeType = upgradeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
