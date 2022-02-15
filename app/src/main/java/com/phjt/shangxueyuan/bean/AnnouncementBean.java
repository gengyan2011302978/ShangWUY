package com.phjt.shangxueyuan.bean;

import java.util.List;


public class AnnouncementBean {
    /**
     * currentPage : 1
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * start : 0
     * list : [{"userName":"王力宏","index":"24"},{"userName":"周杰伦","index":"23"}]
     */

    private int currentPage;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private int start;
    private List<ListBean> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * userName : 王力宏
         * index : 24
         */

        private String userName;
        private String index;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }
    }
}
