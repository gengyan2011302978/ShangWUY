package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

public class MotifDiaryListBean implements Serializable {


    /**
     * id : 1
     * userId : 253
     * photo : null
     * userNick : null
     * choicenessType : 0
     * clockDay : 1
     * content : 111111111111111111111111111
     * motifId : null
     * motifTitle : null
     * createTime : 2021-01-11 16:06:06
     * likeNum : 0
     * whetherTeaching : 0
     * diaryCommentVos : [{"id":3,"userId":15,"photo":null,"userNick":null,"reUserNick":null,"helperType":0,"replyContent":"111","createTime":"2021-01-19 16:32:59","lastCommentId":null},{"id":2,"userId":253,"photo":null,"userNick":null,"reUserNick":"闫大师i","helperType":1,"replyContent":"5555","createTime":"2021-01-19 16:32:49","lastCommentId":1},{"id":1,"userId":14,"photo":null,"userNick":null,"reUserNick":null,"helperType":0,"replyContent":"445","createTime":"2021-01-19 16:32:30","lastCommentId":null}]
     */

    private int id;
    private int userId;
    private String photo;
    private String nickName;
    private int choicenessType;
    private int clockDay;
    private String content;
    private String motifId;
    private String motifTitle;
    private String createTime;
    private String state;
    private String punchCardId;
    private String punchCardName;
    private String coverImg;
    private String diaryImg;
    private String partyNum;
    private String partyUser;
    private String days;
    private int likeNum;
    private int whetherTeaching;
    private List<DiaryCommentVosBean> diaryCommentVos;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPunchCardId() {
        return punchCardId;
    }

    public void setPunchCardId(String punchCardId) {
        this.punchCardId = punchCardId;
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

    public String getDiaryImg() {
        return diaryImg;
    }

    public void setDiaryImg(String diaryImg) {
        this.diaryImg = diaryImg;
    }

    public String getPartyNum() {
        return partyNum;
    }

    public void setPartyNum(String partyNum) {
        this.partyNum = partyNum;
    }

    public String getPartyUser() {
        return partyUser;
    }

    public void setPartyUser(String partyUser) {
        this.partyUser = partyUser;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getChoicenessType() {
        return choicenessType;
    }

    public void setChoicenessType(int choicenessType) {
        this.choicenessType = choicenessType;
    }

    public int getClockDay() {
        return clockDay;
    }

    public void setClockDay(int clockDay) {
        this.clockDay = clockDay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMotifId() {
        return motifId;
    }

    public void setMotifId(String motifId) {
        this.motifId = motifId;
    }

    public String getMotifTitle() {
        return motifTitle;
    }

    public void setMotifTitle(String motifTitle) {
        this.motifTitle = motifTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getWhetherTeaching() {
        return whetherTeaching;
    }

    public void setWhetherTeaching(int whetherTeaching) {
        this.whetherTeaching = whetherTeaching;
    }

    public List<DiaryCommentVosBean> getDiaryCommentVos() {
        return diaryCommentVos;
    }

    public void setDiaryCommentVos(List<DiaryCommentVosBean> diaryCommentVos) {
        this.diaryCommentVos = diaryCommentVos;
    }

    public static class DiaryCommentVosBean {
        /**
         * id : 3
         * userId : 15
         * photo : null
         * userNick : null
         * reUserNick : null
         * helperType : 0
         * replyContent : 111
         * createTime : 2021-01-19 16:32:59
         * lastCommentId : null
         */

        private int id;
        private int userId;
        private String photo;
        private String nickName;
        private String likeNum;
        private String state;
        private String commentVos;
        private String userNick;
        private String reUserNick;
        private int helperType;
        private String replyContent;
        private String createTime;
        private String lastCommentId;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(String likeNum) {
            this.likeNum = likeNum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCommentVos() {
            return commentVos;
        }

        public void setCommentVos(String commentVos) {
            this.commentVos = commentVos;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

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

        public String getUserNick() {
            return userNick;
        }

        public void setUserNick(String userNick) {
            this.userNick = userNick;
        }

        public String getReUserNick() {
            return reUserNick;
        }

        public void setReUserNick(String reUserNick) {
            this.reUserNick = reUserNick;
        }

        public int getHelperType() {
            return helperType;
        }

        public void setHelperType(int helperType) {
            this.helperType = helperType;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getLastCommentId() {
            return lastCommentId;
        }

        public void setLastCommentId(String lastCommentId) {
            this.lastCommentId = lastCommentId;
        }
    }
}
