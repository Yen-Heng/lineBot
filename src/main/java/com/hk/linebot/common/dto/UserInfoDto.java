package com.hk.linebot.common.dto;

import com.hk.linebot.common.enums.ResponseCode;
import com.hk.linebot.common.exception.RestApiException;
import com.hk.linebot.common.response.RestApiResponse;
import org.apache.commons.lang3.StringUtils;

public record UserInfoDto(String userId, String userName) {

    public boolean checkUserInfoDto(){
        if(null==userId || null==userName){
            throw new RestApiException(new RestApiResponse(ResponseCode.PARAMETER_ERROR, this, null));
        }
        if(StringUtils.isBlank(userId) && StringUtils.isBlank(userName))
            throw new RestApiException(new RestApiResponse(ResponseCode.PARAMETER_ERROR, this, null));
        return true;
    }
}
