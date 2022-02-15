package com.phjt.shangxueyuan.bean;

/**
 * @author: gengyan
 * date:    2020/8/28 10:57
 * company: 普华集团
 * description: 描述
 */
public class InvitationRecordBean {

    /**
     * activatedState : 0
     * inviteeMobile : 13100131011
     */

    private Integer activatedState;
    private String inviteeMobile;

    public Integer getActivatedState() {
        return activatedState == null ? -1 : activatedState;
    }

    public void setActivatedState(Integer activatedState) {
        this.activatedState = activatedState;
    }

    public String getInviteeMobile() {
        return inviteeMobile;
    }

    public void setInviteeMobile(String inviteeMobile) {
        this.inviteeMobile = inviteeMobile;
    }
}
