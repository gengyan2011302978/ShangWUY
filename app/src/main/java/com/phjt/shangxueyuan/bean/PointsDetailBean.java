package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/10/21
 * company: 普华集团
 * description:
 */
public class PointsDetailBean {

    private int id;
    private int integralScource;
    private int integralType;
    private String integralDetail;
    private String acquireTime;
    private String createTime;
    private String integralName;
    private int otherType;
    private String otherId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntegralScource() {
        return integralScource;
    }

    public void setIntegralScource(int integralScource) {
        this.integralScource = integralScource;
    }

    public int getIntegralType() {
        return integralType;
    }

    public void setIntegralType(int integralType) {
        this.integralType = integralType;
    }

    public String getIntegralDetail() {
        return integralDetail;
    }

    public void setIntegralDetail(String integralDetail) {
        this.integralDetail = integralDetail;
    }

    public String getAcquireTime() {
        return acquireTime;
    }

    public void setAcquireTime(String acquireTime) {
        this.acquireTime = acquireTime;
    }

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName;
    }

    public int getOtherType() {
        return otherType;
    }

    public void setOtherType(int otherType) {
        this.otherType = otherType;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }
}
