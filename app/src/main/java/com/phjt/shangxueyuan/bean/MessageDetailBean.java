package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/10/28
 * company: 普华集团
 * description:
 */
public class MessageDetailBean {

    private String id;
    private String content;
    private String title;
    private int linkStatus;
    private String link;
    private int messageStatus;
    private int receiveType;
    private int messageType;
    private String createTime;
    private String updateTime;
    private int notesMessageId;
    private String courseId;
    private String topicId;
    private String moduleId;
    private int messageClassify;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLinkStatus() {
        return linkStatus;
    }

    public void setLinkStatus(int linkStatus) {
        this.linkStatus = linkStatus;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getNotesMessageId() {
        return notesMessageId;
    }

    public void setNotesMessageId(int notesMessageId) {
        this.notesMessageId = notesMessageId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public int getMessageClassify() {
        return messageClassify;
    }

    public void setMessageClassify(int messageClassify) {
        this.messageClassify = messageClassify;
    }
}
