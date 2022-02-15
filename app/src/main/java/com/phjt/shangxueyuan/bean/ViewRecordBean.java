package com.phjt.shangxueyuan.bean;

import java.util.List;


public class ViewRecordBean {
    /**
     * records : [{"id":"1","name":"12154545","couDescribe":"吴系挂","coverImg":2,"studyNum":"1436864169","updateTime":"0"},{"id":"1","name":"12154545","couDescribe":"吴系挂","coverImg":2,"studyNum":"1436864169","updateTime":"0"}]
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
        /**
         * id : 1
         * name : 12154545
         * couDescribe : 吴系挂
         * coverImg : 2
         * studyNum : 1436864169
         * updateTime : 0
         */

        private String id;
        private String name;
        private String couDescribe;
        private String coverImg;
        private Integer studyNum;
        private String updateTime;
        private String couId;
        private Integer freeType;
        private Integer delStatus;
        private int upState;

        public int getCouType() {
            return couType;
        }

        public void setCouType(int couType) {
            this.couType = couType;
        }

        private int couType;

        public Integer getDelStatus() {
            return delStatus == null ? 1 : delStatus;
        }

        public void setDelStatus(Integer delStatus) {
            this.delStatus = delStatus;
        }

        public String getCouId() {
            return couId;
        }

        public void setCouId(String couId) {
            this.couId = couId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Integer getStudyNum() {
            return studyNum == null ? 0 : studyNum;
        }

        public void setStudyNum(Integer studyNum) {
            this.studyNum = studyNum;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public Integer getFreeType() {
            return freeType == null ? 0 : freeType;
        }

        public void setFreeType(Integer freeType) {
            this.freeType = freeType;
        }

        public int getUpState() {
            return upState;
        }

        public void setUpState(int upState) {
            this.upState = upState;
        }
    }
}
