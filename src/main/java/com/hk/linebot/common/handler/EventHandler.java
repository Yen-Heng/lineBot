package com.hk.linebot.common.handler;

import com.hk.linebot.service.MessageService;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.spring.boot.handler.annotation.EventMapping;
import com.linecorp.bot.spring.boot.handler.annotation.LineMessageHandler;
import com.linecorp.bot.webhook.model.Event;
import com.linecorp.bot.webhook.model.MessageEvent;

@LineMessageHandler
public class EventHandler {
    private MessagingApiClient messagingApiClient;

    private MessageService messageService;

    public EventHandler(MessagingApiClient messagingApiClient, MessageService messageService) {
        this.messagingApiClient = messagingApiClient;
        this.messageService = messageService;
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent event) {
//        System.out.println("event: " + event);
        messageService.handleTextMessageEvent(event);
    }
}
