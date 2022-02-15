package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class ExchangeCodeBean implements Serializable {


    /**
     * records : [{"id":"1","code":"兑换码","courseDuration":"有效期","courseId":"课程ID","coverImg":"课程封面图","courseName":"课程名称"},{"id":"1","code":"兑换码","courseDuration":"有效期","courseId":"课程ID","coverImg":"课程封面图","courseName":"课程名称"}]
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
         * code : 兑换码
         * courseDuration : 有效期
         * courseId : 课程ID
         * coverImg : 课程封面图
         * courseName : 课程名称
         */

        private String id;
        private String code;
        private String courseDuration;
        private String createTime;
        private String courseId;

        public String getEffectiveState() {
            return effectiveState;
        }

        public void setEffectiveState(String effectiveState) {
            this.effectiveState = effectiveState;
        }

        private String effectiveState;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpState() {
            return upState;
        }

        public void setUpState(String upState) {
            this.upState = upState;
        }

        private String coverImg;
        private String courseName;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String upState;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCourseDuration() {
            return courseDuration;
        }

        public void setCourseDuration(String courseDuration) {
            this.courseDuration = courseDuration;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }
}
