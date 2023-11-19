package com.hk.linebot.entity;

import com.hk.linebot.common.enums.MessageStatus;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class Message {

    private String content;

    private MessageStatus messageStatus;

    @Timestamp
    private LocalDateTime createdTime;

    @Timestamp
    private LocalDateTime updatedTime;
}
