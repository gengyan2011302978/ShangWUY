package com.phjt.plugin_upload

import com.google.gson.Gson

class QyWXMsg {
    String msgtype
    TextBean text

    class TextBean {
        String content
        List mentioned_mobile_list
    }


    String createTextMsg(String content, boolean isAtAll, List<String> notice) {

        if (notice == null) {
            notice = new ArrayList<>()
        }
        if (isAtAll) {
            notice.add('@all')
        }

        TextBean textBean = new TextBean()
        textBean.setContent(content)
        textBean.setMentioned_mobile_list(notice)

        QyWXMsg qyWXMsg = new QyWXMsg()
        qyWXMsg.setMsgtype('text')
        qyWXMsg.setText(textBean)

        return new Gson().toJson(qyWXMsg)
    }
}