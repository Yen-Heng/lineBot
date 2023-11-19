package com.hk.linebot.common.enums;

public enum ResponseCode {

    // common
    SUCCESS("0", "suc"),
    SYSTEM_ERROR("10001", "System error"),
    PARAMETER_ERROR("10002", "Parameter error"),
    SEND_MESSAGE_ERROR("10003", "Send message error"),
    UNABLE_TO_FIND_DATA("10004", "Unable to find data"),
    HAS_NO_SUCH_USER("10005", "Has no such user"),
    RESERVED_ERROR("99999", "RESERVED ERROR DESCRIPTION");

    private final String code;
    private final String description;

    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() { return code; }

    public String getDescription() { return description; }

    @Override
    public String toString() { return code + ": " + description; }
}
