package com.hk.linebot.service;

import com.hk.linebot.common.dto.UserAndMessagesDto;
import com.hk.linebot.common.dto.UserInfoDto;
import com.linecorp.bot.webhook.model.MessageEvent;

import java.util.List;

public interface MessageService {
    boolean sendMessagesById(String id, List<String> message);

    UserAndMessagesDto queryMessagesByUser(UserInfoDto userInfoDto);

    void handleTextMessageEvent(MessageEvent event);

    List<UserAndMessagesDto> queryAllUser();
}
