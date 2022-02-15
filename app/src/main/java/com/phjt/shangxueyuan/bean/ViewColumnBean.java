package com.phjt.shangxueyuan.bean;

import java.util.List;


public class ViewColumnBean {

    /**
     * records : [{"id":80,"name":"tui","couDescribe":"gkli","coverImg":"http://test-k8s-oss.peogoo.com/test-shangwuyou/images/8216030725025083.jpg","studyNum":12,"sumTimeLong":742,"vipState":0,"courseWatchTime":2020}]
     * size : 10
     * current : 1
     * totalPage : 1
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
         * id : 80
         * name : tui
         * couDescribe : gkli
         * coverImg : http://test-k8s-oss.peogoo.com/test-shangwuyou/images/8216030725025083.jpg
         * studyNum : 12
         * sumTimeLong : 742
         * vipState : 0
         * courseWatchTime : 2020
         */

        private int id;
        private String name;
        private String couDescribe;
        private String coverImg;
        private Integer studyNum;
        private Long sumTimeLong;
        private int vipState;
        private Long courseWatchTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getStudyNum() {
            return studyNum;
        }

        public void setStudyNum(int studyNum) {
            this.studyNum = studyNum;
        }

        public Long getSumTimeLong() {
            if (null==sumTimeLong){
                return Long.valueOf(0);
            }
            return sumTimeLong;
        }

        public void setSumTimeLong(Long sumTimeLong) {
            this.sumTimeLong = sumTimeLong;
        }

        public int getVipState() {
            return vipState;
        }

        public void setVipState(int vipState) {
            this.vipState = vipState;
        }

        public Long getCourseWatchTime() {
            return courseWatchTime;
        }

        public void setCourseWatchTime(Long courseWatchTime) {
            this.courseWatchTime = courseWatchTime;
        }
    }
}
