package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2021/6/22
 * company: 普华集团
 * description:
 */
public class CheckTheAnswerBean extends BaseBean {

    private String id;
    private String title;
    private String content;
    private String questionImg;
    private String createTime;
    private String realmName;
    private String realmId;
    private int isReply;
    private int isOpen;
    private TutorAnswer tutorAnswer;
    private UserInfo userInfo;
    private LecLecturer lecLecturer;

    public String getRealmName() {
        return realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public int getIsReply() {
        return isReply;
    }

    public void setIsReply(int isReply) {
        this.isReply = isReply;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public TutorAnswer getTutorAnswer() {
        return tutorAnswer;

    }

    public void setTutorAnswer(TutorAnswer tutorAnswer) {
        this.tutorAnswer = tutorAnswer;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public LecLecturer getLecLecturer() {
        return lecLecturer;
    }

    public void setLecLecturer(LecLecturer lecLecturer) {
        this.lecLecturer = lecLecturer;
    }

    public static class TutorAnswer {
        private String content;
        private String createTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }

    public static class UserInfo {
        private String nickName;
        private String userName;
        private String coverImg;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }
    }

    public static class LecLecturer {
        private String name;
        private String id;
        private String coverImg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }
    }

}
