package com.phjt.shangxueyuan.bean.event;

/**
 * @author: gengyan
 * date:    2020/11/9 19:32
 * company: 普华集团
 * description: 描述
 */
public class CourseCommentStateBean {

    private String id;
    private boolean likeState;
    private int likeNum;
    private int replyNum;

    public CourseCommentStateBean(String id, boolean likeState, int likeNum, int replyNum) {
        this.id = id;
        this.likeState = likeState;
        this.likeNum = likeNum;
        this.replyNum = replyNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLikeState() {
        return likeState;
    }

    public void setLikeState(boolean likeState) {
        this.likeState = likeState;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }
}
