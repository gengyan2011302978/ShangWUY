package com.phjt.shangxueyuan.bean;

import android.content.Intent;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class ThemeMainBean implements Serializable {

    private int size;
    private int current;
    private int totalPage;
    private int totalCount;
    private List<RecordsBean> records;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {

        private int id;
        private String photo;
        private String themeLikeState;
        private String nickName;
        private int vipState;
        private String themeName;
        private String themeImg;
        private String leaveImg;
        private String notesImg;
        private String couImg;
        private String couLikeNum;
        private String couBackNum;
        private String couLikeState;
        private String notesLikeNum;
        private String notesBackNum;
        private String notesLikeState;
        private String leaveLikeNum;
        private String leaveBackNum;
        private String leaveLikeState;
        private String topicName;
        private String pointName;
        private int themeDynamicNum;
        private int themeLikeNum;
        private int themeState;
        private String auditState;
        private String createTime;
        private String content;
        private String couId;
        private String couCommentId;
        private String notesId;
        private String commentId;
        private String leaveId;
        private String pointId;
        private String topicId;
        private String name;
        private String couDescribe;
        private String coverImg;
        private String backContent;
        private String leaveWordContent;
        private String specialTitle;
        private String specialId;
        private String themeSource;
        private String otherType; //0.课程 1.大专栏
        private List<ThemeReplyListBean> themeReplyList;
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOtherType() {
            return otherType;
        }

        public void setOtherType(String otherType) {
            this.otherType = otherType;
        }

        public String getCouLikeNum() {
            return couLikeNum;
        }

        public void setCouLikeNum(String couLikeNum) {
            this.couLikeNum = couLikeNum;
        }

        public String getCouBackNum() {
            return couBackNum;
        }

        public void setCouBackNum(String couBackNum) {
            this.couBackNum = couBackNum;
        }

        public String getCouLikeState() {
            return couLikeState;
        }

        public void setCouLikeState(String couLikeState) {
            this.couLikeState = couLikeState;
        }

        public String getNotesLikeNum() {
            return notesLikeNum;
        }

        public void setNotesLikeNum(String notesLikeNum) {
            this.notesLikeNum = notesLikeNum;
        }

        public String getNotesBackNum() {
            return notesBackNum;
        }

        public void setNotesBackNum(String notesBackNum) {
            this.notesBackNum = notesBackNum;
        }

        public String getNotesLikeState() {
            return notesLikeState;
        }

        public void setNotesLikeState(String notesLikeState) {
            this.notesLikeState = notesLikeState;
        }

        public String getLeaveLikeNum() {
            return leaveLikeNum;
        }

        public void setLeaveLikeNum(String leaveLikeNum) {
            this.leaveLikeNum = leaveLikeNum;
        }

        public String getLeaveBackNum() {
            return leaveBackNum;
        }

        public void setLeaveBackNum(String leaveBackNum) {
            this.leaveBackNum = leaveBackNum;
        }

        public String getLeaveLikeState() {
            return leaveLikeState;
        }

        public void setLeaveLikeState(String leaveLikeState) {
            this.leaveLikeState = leaveLikeState;
        }

        public String getCouImg() {
            return couImg;
        }

        public void setCouImg(String couImg) {
            this.couImg = couImg;
        }

        public String getNotesImg() {
            return notesImg;
        }

        public void setNotesImg(String notesImg) {
            this.notesImg = notesImg;
        }

        public String getPointName() {
            return pointName;
        }

        public void setPointName(String pointName) {
            this.pointName = pointName;
        }

        public String getLeaveImg() {
            return leaveImg;
        }

        public void setLeaveImg(String leaveImg) {
            this.leaveImg = leaveImg;
        }

        public String getCouCommentId() {
            return couCommentId;
        }

        public void setCouCommentId(String couCommentId) {
            this.couCommentId = couCommentId;
        }

        public String getThemeLikeState() {
            return themeLikeState;
        }

        public void setThemeLikeState(String themeLikeState) {
            this.themeLikeState = themeLikeState;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getLeaveId() {
            return leaveId;
        }

        public void setLeaveId(String leaveId) {
            this.leaveId = leaveId;
        }

        public String getNotesId() {
            return notesId;
        }

        public void setNotesId(String notesId) {
            this.notesId = notesId;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public String getThemeSource() {
            return themeSource;
        }

        public void setThemeSource(String themeSource) {
            this.themeSource = themeSource;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getVipState() {
            return vipState;
        }

        public void setVipState(int vipState) {
            this.vipState = vipState;
        }

        public String getThemeName() {
            return themeName;
        }

        public void setThemeName(String themeName) {
            this.themeName = themeName;
        }

        public String getThemeImg() {
            return themeImg;
        }

        public void setThemeImg(String themeImg) {
            this.themeImg = themeImg;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public int getThemeDynamicNum() {
            return themeDynamicNum;
        }

        public void setThemeDynamicNum(int themeDynamicNum) {
            this.themeDynamicNum = themeDynamicNum;
        }

        public int getThemeLikeNum() {
            return themeLikeNum;
        }

        public void setThemeLikeNum(int themeLikeNum) {
            this.themeLikeNum = themeLikeNum;
        }

        public int getThemeState() {
            return themeState;
        }

        public void setThemeState(int themeState) {
            this.themeState = themeState;
        }

        public String getAuditState() {
            return auditState;
        }

        public void setAuditState(String auditState) {
            this.auditState = auditState;
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

        public String getCouId() {
            return couId;
        }

        public void setCouId(String couId) {
            this.couId = couId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCouDescribe() {
            return couDescribe;
        }

        public void setCouDescribe(String couDescribe) {
            this.couDescribe = couDescribe;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getBackContent() {
            return backContent;
        }

        public void setBackContent(String backContent) {
            this.backContent = backContent;
        }

        public String getLeaveWordContent() {
            return leaveWordContent;
        }

        public void setLeaveWordContent(String leaveWordContent) {
            this.leaveWordContent = leaveWordContent;
        }

        public String getSpecialTitle() {
            return specialTitle;
        }

        public void setSpecialTitle(String specialTitle) {
            this.specialTitle = specialTitle;
        }

        public String getSpecialId() {
            return specialId;
        }

        public void setSpecialId(String specialId) {
            this.specialId = specialId;
        }

        public List<ThemeReplyListBean> getThemeReplyList() {
            return themeReplyList;
        }

        public void setThemeReplyList(List<ThemeReplyListBean> themeReplyList) {
            this.themeReplyList = themeReplyList;
        }

        public static class ThemeReplyListBean {

            private int replyId;
            private String replyPhoto;
            private String replyNickName;
            private int replyVipState;
            private String themeReplyContent;
            private String byReplyNickName;

            public int getReplyId() {
                return replyId;
            }

            public void setReplyId(int replyId) {
                this.replyId = replyId;
            }

            public String getReplyPhoto() {
                return replyPhoto;
            }

            public void setReplyPhoto(String replyPhoto) {
                this.replyPhoto = replyPhoto;
            }

            public String getReplyNickName() {
                return replyNickName;
            }

            public void setReplyNickName(String replyNickName) {
                this.replyNickName = replyNickName;
            }

            public int getReplyVipState() {
                return replyVipState;
            }

            public void setReplyVipState(int replyVipState) {
                this.replyVipState = replyVipState;
            }

            public String getThemeReplyContent() {
                return themeReplyContent;
            }

            public void setThemeReplyContent(String themeReplyContent) {
                this.themeReplyContent = themeReplyContent;
            }

            public String getByReplyNickName() {
                return byReplyNickName;
            }

            public void setByReplyNickName(String byReplyNickName) {
                this.byReplyNickName = byReplyNickName;
            }
        }
    }
}
