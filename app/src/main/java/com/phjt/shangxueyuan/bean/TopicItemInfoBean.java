package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class TopicItemInfoBean implements Serializable {

    /**
     * records : [{"id":2,"photo":" 用户头像","nickName":"用户昵称","vipState":"vip状态","themeName":"内容","themeImg":"图片","topicName":"话题名称","themeDynamicNum":"评论数","themeLikeNum":"点赞数","themeState":" 置顶状态0不推荐1推荐","auditState":" 审核状态","createTime":"发布时间"}]
     * size : 10
     * current : 1
     * totalPage : 1
     * totalCount : 1
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
        /**
         * id : 2
         * photo :  用户头像
         * nickName : 用户昵称
         * vipState : vip状态
         * themeName : 内容
         * themeImg : 图片
         * topicName : 话题名称
         * themeDynamicNum : 评论数
         * themeLikeNum : 点赞数
         * themeState :  置顶状态0不推荐1推荐
         * auditState :  审核状态
         * createTime : 发布时间
         */

        private int id;
        private String photo;
        private String nickName;
        private String vipState;
        private String themeName;
        private String themeImg;
        private String topicName;
        private String themeDynamicNum;
        private Integer themeLikeNum;
        private String themeState;
        private String auditState;
        private String isAdministrator;
        private String createTime;
        private String likeState;

        public String getIsAdministrator() {
            return isAdministrator;
        }

        public void setIsAdministrator(String isAdministrator) {
            this.isAdministrator = isAdministrator;
        }

        public String getLikeState() {
            return likeState;
        }

        public void setLikeState(String likeState) {
            this.likeState = likeState;
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

        public String getVipState() {
            return vipState;
        }

        public void setVipState(String vipState) {
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

        public String getThemeDynamicNum() {
            return themeDynamicNum;
        }

        public void setThemeDynamicNum(String themeDynamicNum) {
            this.themeDynamicNum = themeDynamicNum;
        }

        public Integer getThemeLikeNum() {
            return themeLikeNum;
        }

        public void setThemeLikeNum(Integer themeLikeNum) {
            this.themeLikeNum = themeLikeNum;
        }

        public String getThemeState() {
            return themeState;
        }

        public void setThemeState(String themeState) {
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
    }
}
