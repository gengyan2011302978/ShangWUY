package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

public class RankBean implements Serializable {


    /**
     * id : 1
     * punchCardName : 打卡1
     * coverImg : http://test-k8s-oss.peogoo.com/test-shangwuyou/images/9815889243240535.jpg
     * partyUser : 2
     * userVos : [{"userId":253,"photo":null,"nickName":"123","partyNum":2},{"userId":14,"photo":null,"nickName":"闫大师i","partyNum":1}]
     */

    private int id;
    private String punchCardName;
    private String coverImg;
    private String nickName;
    private String rankingNum;
    private int partyUser;
    private int partyNum;
    private List<UserVosBean> userVos;

    public String getRankingNum() {
        return rankingNum;
    }

    public void setRankingNum(String rankingNum) {
        this.rankingNum = rankingNum;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(int partyNum) {
        this.partyNum = partyNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPunchCardName() {
        return punchCardName;
    }

    public void setPunchCardName(String punchCardName) {
        this.punchCardName = punchCardName;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public int getPartyUser() {
        return partyUser;
    }

    public void setPartyUser(int partyUser) {
        this.partyUser = partyUser;
    }

    public List<UserVosBean> getUserVos() {
        return userVos;
    }

    public void setUserVos(List<UserVosBean> userVos) {
        this.userVos = userVos;
    }

    public static class UserVosBean {
        /**
         * userId : 253
         * photo : null
         * nickName : 123
         * partyNum : 2
         */

        private int userId;
        private String photo;
        private String nickName;
        private int partyNum;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public int getPartyNum() {
            return partyNum;
        }

        public void setPartyNum(int partyNum) {
            this.partyNum = partyNum;
        }
    }
}
