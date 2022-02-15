package com.phjt.shangxueyuan.bean;

/**
 * @author: Roy
 * date:   2020/12/10
 * company: 普华集团
 * description:优惠券到期通知 实体类
 */
public class ExpirationNoticeBean {
    private NormalNotice normalNotice;
    private ExpiredNotice expiredNotice;

    public NormalNotice getNormalNotice() {
        return normalNotice;
    }

    public void setNormalNotice(NormalNotice normalNotice) {
        this.normalNotice = normalNotice;
    }

    public ExpiredNotice getExpiredNotice() {
        return expiredNotice;
    }

    public void setExpiredNotice(ExpiredNotice expiredNotice) {
        this.expiredNotice = expiredNotice;
    }

    public class NormalNotice {
        public String getTile() {
            return tile;
        }

        public void setTile(String tile) {
            this.tile = tile;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String tile;
        private String content;
    }

    public class ExpiredNotice {
        public String getTile() {
            return tile;
        }

        public void setTile(String tile) {
            this.tile = tile;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String tile;
        private String content;
    }
}
