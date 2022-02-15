package com.phjt.shangxueyuan.bean;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class TutorAnsweringQuestionsBean extends BaseBean {
    private String id;
    private String name;
    private String realmId;
    private String consultTeachcerId;
    private String questionCoin;
    private String coverImg;
    private String lecDesc;
    private int frozenStatus;
    private List<RealmList> specialityRealmList;

    public String getConsultTeachcerId() {
        return consultTeachcerId;
    }

    public void setConsultTeachcerId(String consultTeachcerId) {
        this.consultTeachcerId = consultTeachcerId;
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

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getQuestionCoin() {
        return questionCoin;
    }

    public void setQuestionCoin(String questionCoin) {
        this.questionCoin = questionCoin;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getLecDesc() {
        return lecDesc;
    }

    public void setLecDesc(String lecDesc) {
        this.lecDesc = lecDesc;
    }

    public List<RealmList> getSpecialityRealmList() {
        return specialityRealmList;
    }

    public void setSpecialityRealmList(List<RealmList> specialityRealmList) {
        this.specialityRealmList = specialityRealmList;
    }

    public int getFrozenStatus() {
        return frozenStatus;
    }

    public void setFrozenStatus(int frozenStatus) {
        this.frozenStatus = frozenStatus;
    }

    public static class RealmList {
        private String id;
        private String realmName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRealmName() {
            return realmName;
        }

        public void setRealmName(String realmName) {
            this.realmName = realmName;
        }
    }

}
