package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roy
 * date   2020/6/1
 * company 普华集团
 * description:
 */
public class VipExchangeCodeBean implements Serializable {


    /**
     * records : [{"id":"1","code":"兑换码","commodityId":"会员ID","commodityName":"会员名称"},{"id":"1","code":"兑换码","commodityId":"会员ID","commodityName":"会员名称"}]
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
         * commodityId : 会员ID
         * commodityName : 会员名称
         */

        private String id;
        private String code;
        private String commodityId;
        private String commodityName;

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

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }
    }
}
