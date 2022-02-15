package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

public class MessageListBean implements Serializable {

    /**
     * records : [{"id":"1","title":"12154545","content":"吴系挂","link_status":2,"link":"1436864169","createTime":"2019-12-13 18:00:00"}]
     * size : 2
     * current : 1
     * totalPage : 2
     * totalCount : 3
     */

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

        private String id;
        private String title;
        private String content;
        private int linkStatus;
        private int messageType;
        private String link;
        private String createTime;
        private String courseId;
        private int messageClassify;
        private int messageId;
        private String otherId;
        private String topicId;
        private int mouduleId;

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public int getLinkStatus() {
            return linkStatus;
        }

        public void setLinkStatus(int linkStatus) {
            this.linkStatus = linkStatus;
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


        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public int getMessageClassify() {
            return messageClassify;
        }

        public void setMessageClassify(int messageClassify) {
            this.messageClassify = messageClassify;
        }

        public int getMessageId() {
            return messageId;
        }

        public void setMessageId(int messageId) {
            this.messageId = messageId;
        }

        public String getOtherId() {
            return otherId;
        }

        public void setOtherId(String otherId) {
            this.otherId = otherId;
        }

        public int getMouduleId() {
            return mouduleId;
        }

        public void setMouduleId(int mouduleId) {
            this.mouduleId = mouduleId;
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }
    }
}
