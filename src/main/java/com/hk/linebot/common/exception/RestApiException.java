package com.hk.linebot.common.exception;

import com.hk.linebot.common.response.RestApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class RestApiException extends RuntimeException {

    RestApiResponse apiRes;

    public RestApiException(RestApiResponse apiRes) {
        super(apiRes.toString());
        this.apiRes = apiRes;
    }
}