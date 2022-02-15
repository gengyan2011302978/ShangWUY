package com.phjt.shangxueyuan.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: Roy
 * date:   2020/5/7
 * company: 普华集团
 * description:初/中/高级课程实体类
 */
public class CourseClassifyBean extends BaseBean {
    private int id;
    private String name;
    private int level;
    private String description;
    private String researchChannelDesc;
    private List<ChildListBean> childList;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResearchChannelDesc() {
        return researchChannelDesc;
    }

    public void setResearchChannelDesc(String researchChannelDesc) {
        this.researchChannelDesc = researchChannelDesc;
    }

    public List<ChildListBean> getChildList() {
        return childList;
    }

    public void setChildList(List<ChildListBean> childList) {
        this.childList = childList;
    }

    public static class ChildListBean {
        /**
         * id : 45
         * name : 主修课
         * level : 2
         * description : 123士大夫
         * researchChannelDesc : {"boldText":"BOC高级课程","ordinaryText":"是一门很好的课程"}
         * sumTimeLong : 0
         * totalWatchDuration : 0
         * state : 1
         * latestWatch : null
         */

        private int id;
        private String name;
        private int level;
        private String description;
        private String researchChannelDesc;
        private long sumTimeLong;
        private long totalWatchDuration;
        private int state;
        private LatestWatch latestWatch;

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

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getResearchChannelDesc() {
            return researchChannelDesc;
        }

        public void setResearchChannelDesc(String researchChannelDesc) {
            this.researchChannelDesc = researchChannelDesc;
        }

        public long getSumTimeLong() {
            return sumTimeLong;
        }

        public void setSumTimeLong(long sumTimeLong) {
            this.sumTimeLong = sumTimeLong;
        }

        public long getTotalWatchDuration() {
            return totalWatchDuration;
        }

        public void setTotalWatchDuration(long totalWatchDuration) {
            this.totalWatchDuration = totalWatchDuration;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public LatestWatch getLatestWatch() {
            return latestWatch;
        }

        public void setLatestWatch(LatestWatch latestWatch) {
            this.latestWatch = latestWatch;
        }
    }

    public static class LatestWatch {
        public String id;
        public String name;
        public long sumTimeLong;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getSumTimeLong() {
            return sumTimeLong;
        }

        public void setSumTimeLong(long sumTimeLong) {
            this.sumTimeLong = sumTimeLong;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
