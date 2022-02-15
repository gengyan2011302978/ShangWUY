package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: Roy
 * date:   2020/4/1
 * company: 普华集团
 * description:
 */
public class BaseListBean<T> {

    private int size;
    private int totalPage;
    private int current;
    private int pageCount;;
    private int totalCount;;
    private List<T> records;

    public int getSize() {
        return size;
    }

    public void setSize(int pageSize) {
        this.size = pageSize;
    }


    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int urrent) {
        this.current = urrent;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
