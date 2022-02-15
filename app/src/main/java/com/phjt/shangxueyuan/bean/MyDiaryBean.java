package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/1/27
 * company: 普华集团
 * description:
 */
public class MyDiaryBean {

    private String id;
    private String userId;
    private String photo;
    private String nickName;
    private String choicenessType;
    private String clockDay;
    private String content;
    private String motifId;
    private String motifTitle;
    private String createTime;
    private String punchCardId;
    private String diaryImg;
    private int likeNum;
    private int whetherTeaching;
    private int state;
    private List<DiaryCommentVos> diaryCommentVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getChoicenessType() {
        return choicenessType;
    }

    public void setChoicenessType(String choicenessType) {
        this.choicenessType = choicenessType;
    }

    public String getClockDay() {
        return clockDay;
    }

    public void setClockDay(String clockDay) {
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<DiaryCommentVos> getDiaryCommentVos() {
        return diaryCommentVos;
    }

    public String getDiaryImg() {
        return diaryImg;
    }

    public void setDiaryImg(String diaryImg) {
        this.diaryImg = diaryImg;
    }

    public void setDiaryCommentVos(List<DiaryCommentVos> diaryCommentVos) {
        this.diaryCommentVos = diaryCommentVos;
    }

    public String getPunchCardId() {
        return punchCardId;
    }

    public void setPunchCardId(String punchCardId) {
        this.punchCardId = punchCardId;
    }

    public   class DiaryCommentVos{
        private String id;
        private String photo;
        private String userNick;
        private String reUserNick;
        private int helperType;
        private String replyContent;
        private String createTime;
        private String nickName;

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

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
