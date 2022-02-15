package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: gengyan
 * date:    2021/1/30 18:50
 * company: 普华集团
 * description: 训练营评论实体
 */
public class TrainingCommentBean implements Serializable {

    private List<DiaryListBean> diaryList;

    public List<DiaryListBean> getDiaryList() {
        return diaryList;
    }

    public void setDiaryList(List<DiaryListBean> diaryList) {
        this.diaryList = diaryList;
    }

    public static class DiaryListBean implements Serializable {
        /**
         * id : 27
         * userId : 264
         * photo : null
         * nickname : 152****9026
         * createTime : 2021-01-30 16:50:49
         * content : 测试任务下发表评论4444
         * likeNum : 1
         * commentNum : 1
         * diaryImg : null
         * likestatus : 1
         * replyVoList : [{"userId":264,"photo":null,"nickname":"152****9026","id":44,"createTime":"2021-01-30 16:52:06","replyContent":"测试在日记下回复测试回复信息电视剧福建省评论详情下回复评论信息，在的发生的方式","likeNum":1,"likestatus":1}]
         */

        private String id;
        private String userId;
        private String photo;
        private String nickname;
        private String createTime;
        private String content;
        private Integer likeNum;
        private Integer commentNum;
        private String diaryImg;
        private Integer likestatus;
        private Integer vipState;
        private List<ReplyVoListBean> replyVoList;

        private int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Integer getVipState() {
            return vipState == null ? 0 : vipState;
        }

        public void setVipState(Integer vipState) {
            this.vipState = vipState;
        }

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getLikeNum() {
            return likeNum == null ? 0 : likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public Integer getCommentNum() {
            return commentNum == null ? 0 : commentNum;
        }

        public void setCommentNum(Integer commentNum) {
            this.commentNum = commentNum;
        }

        public String getDiaryImg() {
            return diaryImg;
        }

        public void setDiaryImg(String diaryImg) {
            this.diaryImg = diaryImg;
        }

        public Integer getLikestatus() {
            return likestatus == null ? 0 : likestatus;
        }

        public void setLikestatus(Integer likestatus) {
            this.likestatus = likestatus;
        }

        public List<ReplyVoListBean> getReplyVoList() {
            return replyVoList;
        }

        public void setReplyVoList(List<ReplyVoListBean> replyVoList) {
            this.replyVoList = replyVoList;
        }
    }

    public static class ReplyVoListBean implements Serializable {
        /**
         * userId : 264
         * photo : null
         * nickname : 152****9026
         * id : 44
         * createTime : 2021-01-30 16:52:06
         * replyContent : 测试在日记下回复测试回复信息电视剧福建省评论详情下回复评论信息，在的发生的方式
         * likeNum : 1
         * likestatus : 1
         */

        private String userId;
        private String photo;
        private String nickname;
        private String id;
        private String createTime;
        private String replyContent;
        private Integer likeNum;
        private Integer likestatus;
        private Integer vipState;

        public Integer getVipState() {
            return vipState == null ? 0 : vipState;
        }

        public void setVipState(Integer vipState) {
            this.vipState = vipState;
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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public Integer getLikeNum() {
            return likeNum == null ? 0 : likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public Integer getLikestatus() {
            return likestatus == null ? 0 : likestatus;
        }

        public void setLikestatus(Integer likestatus) {
            this.likestatus = likestatus;
        }
    }
}
