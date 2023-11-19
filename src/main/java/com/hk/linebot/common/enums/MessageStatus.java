package com.hk.linebot.common.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum MessageStatus {

    SYSTEM_MESSAGE(0, "System message"),
    CUSTOMER_MESSAGE(1, "Customer message"),
    REGULAR_MESSAGES(2, "Regular messages"),
    OTHERS(3, "Others");

    private final int value;
    private final String text;

    MessageStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public static MessageStatus of(int value) {
        return Stream.of(MessageStatus.values())
                .filter(s -> s.value == value)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static String getText(int value) {
        return Stream.of(MessageStatus.values())
                .filter(s -> s.value == value)
                .map(s -> s.text)
                .findFirst()
                .orElse("");
    }
}
