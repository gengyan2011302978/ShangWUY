package com.mzmedia.presentation.dto;

import com.mengzhu.live.sdk.business.dto.chat.impl.ChatMegTxtDto;

/**
 * @author: gengyan
 * date:    2021/8/20 10:36
 * company: 普华集团
 * description: 描述
 */
public class ChatMegExpandDto extends ChatMegTxtDto {

    private String unique_id;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }
}
