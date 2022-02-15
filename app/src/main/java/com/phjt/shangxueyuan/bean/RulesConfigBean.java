package com.phjt.shangxueyuan.bean;

import java.io.Serializable;

public class RulesConfigBean implements Serializable {


    /**
     * id : 1
     * title : 熵吾优APP推广返佣提现规则
     * content : 1.如何成为推广人？
     凡熵吾优APP已注册用户均可参与推广活动
     2.哪些人可以被邀请？
     未在熵吾优APP注册过
     未在熵吾优APP的互联网广告页面中留 n3.推广佣金如何计算？
     您邀请的好友，在熵吾优APP成功购买符合提佣条件的订单，即可返回好友符合条件订单的实付金额的10%到您的钱包中，如好友通过苹果终端支付，会扣除好友实付金额的30%，之后剩余 现到您的钱包
     4.我获得的佣金需要自行缴税吗？
     根据《中华人民共和国个人所得税》等相关法律法规的规定，熵吾优将为您代缴相关税费。在您申请提现后，熵吾优将扣缴相关税费后的提现金额转账至您的银行卡 果您是公司用户，需提供对应金额的服务费发票
     5.我与好友的绑定周期是多久？
     永久。只要好友是通过您邀请带来的，无论多久，好友的成单都给您提佣
     6.冻结中佣金是什么？
     好友支付后的3 期，期间好友退款后您的佣金也将被扣除，第36天可发起提现申请
     7.如何进行提现？
     通过【我的】-【我的钱包】查看有待可提现金额，点击页面的【申请提现】按钮，进入提现确认页面，进行银行卡绑定和提 。
     8.提现后多久到账？会提现到哪里？
     提现后需要经过人工审核和支付，预计在35个工作日内到账，你可在提现记录中查看是否提现成功。佣金将支付到您绑定的银行卡账号中，查询路径为：熵吾优APP【我 】中的【提现记录】
     9.佣金有失效时间吗？
     有，您需要在佣金可提现之日起30天内提现，否则佣金将过期而无法提现
     10.我邀请了好友，好友又邀请了其他人，我可以获得佣金吗？
     不可以， 邀请的好友中获得佣金
     11.我自己购买课程后，可以获得佣金吗？
     不可以，只能通过好友购课后获得佣金
     12.对本次活动有任何问题，可拨打13522275371（工作日9:00-18:00） .在法律允许范围内，活动最终解释权归属于普华众鑫教育科技（北京）有限公司
     * type : 1
     * sort : null
     * createTime : 2020-08-14 14:42:16
     * updateTime : 2020-08-18 14:45:50
     */

    private int id;
    private String title;
    private String content;
    private String type;
    private Object sort;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
