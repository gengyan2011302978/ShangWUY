package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class TopicListBean implements Serializable {


    /**
     * records : [{"id":2,"coverImg":" 封面图","topicName":"话题名称","focusDescribe":"话题索引描述","topicDynamicNum":"动态评论数","upState":"上下架状态"}]
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
         * coverImg :  封面图
         * topicName : 话题名称
         * focusDescribe : 话题索引描述
         * topicDynamicNum : 动态评论数
         * upState : 上下架状态
         */

        private int id;
        private String coverImg;
        private String topicName;
        private String focusDescribe;
        private String topicDynamicNum;
        private String upState;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getFocusDescribe() {
            return focusDescribe;
        }

        public void setFocusDescribe(String focusDescribe) {
            this.focusDescribe = focusDescribe;
        }

        public String getTopicDynamicNum() {
            return topicDynamicNum;
        }

        public void setTopicDynamicNum(String topicDynamicNum) {
            this.topicDynamicNum = topicDynamicNum;
        }

        public String getUpState() {
            return upState;
        }

        public void setUpState(String upState) {
            this.upState = upState;
        }
    }
}
