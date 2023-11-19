package com.hk.linebot.common.dto;

import com.hk.linebot.entity.Message;
import java.util.List;
import java.util.Objects;

public record UserAndMessagesDto(String id, String userName, boolean enabled, List<Message> messages) {
    public UserAndMessagesDto {
        Objects.requireNonNull(id, "Has no userId");
//        Objects.requireNonNull(userName, "Has no userName");
    }
}
