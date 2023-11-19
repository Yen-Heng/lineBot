package com.hk.linebot.common.dto;

import java.util.List;

public record SendMessageDto(String id, List<String> messages) {
}
