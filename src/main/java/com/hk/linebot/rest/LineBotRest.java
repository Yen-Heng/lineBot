package com.hk.linebot.rest;

import com.hk.linebot.common.dto.SendMessageDto;
import com.hk.linebot.common.enums.ResponseCode;
import com.hk.linebot.common.response.RestApiResponse;
import com.hk.linebot.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name = "LineBot", description = "Line Bot API")
@RestController
@RequestMapping(value = "/v1/lineBot", produces = "application/json;charset=utf-8")
public class LineBotRest {

    private MessageService messageService;

    public LineBotRest(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(description = "Send line message using userId.")
    @PostMapping(value = "/sendMessagesById")
    private ResponseEntity<Object> sendMessagesById(@RequestBody SendMessageDto sendMessageDto){
        if(messageService.sendMessagesById(sendMessageDto.id(), sendMessageDto.messages()))
            return ResponseEntity.ok(new RestApiResponse(ResponseCode.SUCCESS, null));
        else
            return ResponseEntity.ok(new RestApiResponse(ResponseCode.SEND_MESSAGE_ERROR, null));
    }
}
