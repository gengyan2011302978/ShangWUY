package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/4/1 16:12
 * company: 普华集团
 * description: 描述
 */
public class BaseCourseListBean<T> {

    private Integer totalCount;
    private Integer totalPage;
    private List<T> records;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getTotalCount() {
        return totalCount == null ? 0 : totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage == null ? 0 : totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
