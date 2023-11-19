package com.hk.linebot.rest;

import com.hk.linebot.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;



@Tag(name = "LineBot", description = "Line Bot API")
@RestController
@RequestMapping(value = "/v1/lineBot", produces = "application/json;charset=utf-8")
public class LineBotRest {

    private MessageService messageService;

    public LineBotRest(MessageService messageService) {
        this.messageService = messageService;
    }

}
