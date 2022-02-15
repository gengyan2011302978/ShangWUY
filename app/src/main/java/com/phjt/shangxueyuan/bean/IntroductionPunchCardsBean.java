package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/1/30
 * company: 普华集团
 * description:
 */
public class IntroductionPunchCardsBean {

    private String id;
    private String punchCardName;
    private String punchCardStartDate;
    private String punchCardEndDate;
    private String punchCardDesc;

    private List<CouLinkVos> couLinkVos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPunchCardName() {
        return punchCardName;
    }

    public void setPunchCardName(String punchCardName) {
        this.punchCardName = punchCardName;
    }

    public String getPunchCardStartDate() {
        return punchCardStartDate;
    }

    public void setPunchCardStartDate(String punchCardStartDate) {
        this.punchCardStartDate = punchCardStartDate;
    }

    public String getPunchCardEndDate() {
        return punchCardEndDate;
    }

    public void setPunchCardEndDate(String punchCardEndDate) {
        this.punchCardEndDate = punchCardEndDate;
    }

    public String getPunchCardDesc() {
        return punchCardDesc;
    }

    public void setPunchCardDesc(String punchCardDesc) {
        this.punchCardDesc = punchCardDesc;
    }

    public List<CouLinkVos> getCouLinkVos() {
        return couLinkVos;
    }

    public void setCouLinkVos(List<CouLinkVos> couLinkVos) {
        this.couLinkVos = couLinkVos;
    }

    public class CouLinkVos {
        private String id;
        private String name;
        private String coverImg;
        private String couDesc;
        private String studyNum;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getCouDesc() {
            return couDesc;
        }

        public void setCouDesc(String couDesc) {
            this.couDesc = couDesc;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getStudyNum() {
            return studyNum;
        }

        public void setStudyNum(String studyNum) {
            this.studyNum = studyNum;
        }
    }
}
