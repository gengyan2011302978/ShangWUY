package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/10/28
 * company: 普华集团
 * description:
 */
public class IntegralRankingBean {
    private int id;
    private int rowNo;
    private String userStudyCoin;
    private String userBocc;
    private String nickName;
    private String photo;

    public String getUserBocc() {
        return userBocc;
    }

    public void setUserBocc(String userBocc) {
        this.userBocc = userBocc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserIntegral() {
        return userStudyCoin;
    }

    public void setUserIntegral(String userIntegral) {
        this.userStudyCoin = userIntegral;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }
}
