package com.phjt.shangxueyuan.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ArticleListBean implements Serializable {


    /**
     * records : [{"id":5,"classifyId":3,"articleTitle":"国学研究","articleDesc":"国学研究123","articleImg":"http://test-k8s-oss.peogoo.com/test-shangwuyou/images/5415888280326685.jpg","articleNum":1001,"articleLabel":"国学","articleTop":1,"classifyName":"国学精华"}]
     * size : 10
     * current : 1
     * totalCount : 3
     * totalPage : 1
     */

    private int size;
    private int current;
    private int totalCount;
    private int totalPage;
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

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public static class RecordsBean {
        /**
         * id : 5
         * classifyId : 3
         * articleTitle : 国学研究
         * articleDesc : 国学研究123
         * articleImg : http://test-k8s-oss.peogoo.com/test-shangwuyou/images/5415888280326685.jpg
         * articleNum : 1001
         * articleLabel : 国学
         * articleTop : 1
         * classifyName : 国学精华
         */

        private String id;
        private int classifyId;
        private String articleTitle;
        private String articleDesc;
        private String articleImg;
        private String articleNum;
        private String articleLabel;
        private int articleTop;
        private String classifyName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getClassifyId() {
            return classifyId;
        }

        public void setClassifyId(int classifyId) {
            this.classifyId = classifyId;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleDesc() {
            return articleDesc;
        }

        public void setArticleDesc(String articleDesc) {
            this.articleDesc = articleDesc;
        }

        public String getArticleImg() {
            return articleImg;
        }

        public void setArticleImg(String articleImg) {
            this.articleImg = articleImg;
        }

        public String getArticleNum() {
            int mStudyNum = Integer.parseInt(articleNum);
            if (mStudyNum<1000){
                return articleNum;
            }else if (mStudyNum<10000){
                Double aDouble = new BigDecimal((float)mStudyNum/1000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if(aDouble.intValue()-aDouble==0) {//判断是否符合取整条件
                    if (aDouble>=10){
                        return "1万";
                    }
                    return (aDouble.intValue()+"k");
                }else {
                    return (aDouble+"k");
                }
            }else if (mStudyNum<100000){
                Double aDouble = new BigDecimal((float)mStudyNum/10000).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                if(aDouble.intValue()-aDouble==0) {//判断是否符合取整条件
                    return (aDouble.intValue()+"万");
                }else {
                    return (aDouble+"万");
                }
            }else if (mStudyNum==100000){
                return "10万";
            }else if (mStudyNum>100000){
                return "10万+";
            }
            return articleNum;
        }

        public void setArticleNum(String articleNum) {
            this.articleNum = articleNum;
        }

        public String getArticleLabel() {
            return articleLabel;
        }

        public void setArticleLabel(String articleLabel) {
            this.articleLabel = articleLabel;
        }

        public int getArticleTop() {
            return articleTop;
        }

        public void setArticleTop(int articleTop) {
            this.articleTop = articleTop;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }
    }
}
