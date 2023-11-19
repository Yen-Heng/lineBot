package com.hk.linebot.service.impl;

import com.hk.linebot.common.config.LineMessageConfig;
import com.hk.linebot.common.dto.UserAndMessagesDto;
import com.hk.linebot.common.dto.UserInfoDto;
import com.hk.linebot.common.enums.MessageStatus;
import com.hk.linebot.common.enums.ResponseCode;
import com.hk.linebot.common.exception.RestApiException;
import com.hk.linebot.common.response.RestApiResponse;
import com.hk.linebot.dao.UserRepository;
import com.hk.linebot.entity.User;
import com.hk.linebot.service.MessageService;
import com.linecorp.bot.client.base.Result;
import com.linecorp.bot.messaging.client.MessagingApiClient;
import com.linecorp.bot.messaging.model.*;
import com.linecorp.bot.webhook.model.MessageEvent;
import com.linecorp.bot.webhook.model.TextMessageContent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private UUID uuid = UUID.randomUUID();

    private UserRepository userRepository;

    private MessagingApiClient messagingApiClient;

    public MessageServiceImpl(UserRepository userRepository, MessagingApiClient messagingApiClient) {
        this.userRepository = userRepository;
        this.messagingApiClient = messagingApiClient;
    }

    @Override
    public boolean sendMessagesById(String id, List<String> messageList){
        User user = userRepository.findById(id).orElseThrow(() ->
                        new RestApiException(new RestApiResponse(ResponseCode.HAS_NO_SUCH_USER, null, null))
        );
        String lineToken = user.getLineToken();
        MessagingApiClient client = MessagingApiClient
                .builder(LineMessageConfig.INSTANCE.getChannelToken()).build();

        List<Message> messages = messageList.stream()
                        .filter(m -> !m.isBlank())
                        .map(m -> new TextMessage(m))
                        .collect(Collectors.toList());
        PushMessageRequest pushMessage = new PushMessageRequest(lineToken, messages, false, null);
        CompletableFuture<Result<PushMessageResponse>> msgMap;
        try {
            msgMap = client.pushMessage(uuid, pushMessage);
            Result<PushMessageResponse> result = msgMap.get();
            result.body().sentMessages().stream().forEach(
                    m->System.out.println(m)
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public UserAndMessagesDto queryMessagesByUser(UserInfoDto userInfoDto) {
        userInfoDto.checkUserInfoDto();
        User user = userRepository.findByIdOrUserName(userInfoDto.userId(), userInfoDto.userName()).orElseThrow(() ->
                new RestApiException(new RestApiResponse(ResponseCode.UNABLE_TO_FIND_DATA, null, null))
        );
        return new UserAndMessagesDto(user.getId().toHexString(), user.getUserName(), user.isEnabled(), user.getMessages());
    }

    @Override
    public void handleTextMessageEvent(MessageEvent event) {
        if (event.message() instanceof TextMessageContent && !event.source().userId().isBlank()) {
            TextMessageContent message = (TextMessageContent) event.message();
            String lineToken = event.source().userId();
            String originalMessageText = message.text();
            User user;
            Optional<User> oUser = userRepository.findByLineToken(lineToken);
            if(confirmUserExists(oUser)){
                user = oUser.get();
                user.getMessages().add(com.hk.linebot.entity.Message.builder().content(originalMessageText)
                        .messageStatus(MessageStatus.CUSTOMER_MESSAGE)
                        .createdTime(LocalDateTime.now())
                        .updatedTime(LocalDateTime.now())
                        .build());
            }else {
                user = User.builder()
                        .enabled(true)
                        .lineToken(lineToken)
                        .messages(Arrays.asList(com.hk.linebot.entity.Message.builder().content(originalMessageText)
                            .messageStatus(MessageStatus.CUSTOMER_MESSAGE)
                            .createdTime(LocalDateTime.now())
                            .updatedTime(LocalDateTime.now())
                            .build())).build();
            }
            User result = userRepository.save(user);
            if(null!=result)
                messagingApiClient.replyMessage(new ReplyMessageRequest(
                        event.replyToken(),
                        List.of(new TextMessage("Message has been received:"+originalMessageText)), false));
        }
    }

    @Override
    public List<UserAndMessagesDto> queryAllUser() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
            return null;
        return users.stream().map(user ->
                new UserAndMessagesDto(user.getId().toHexString(), user.getUserName(), user.isEnabled(), null))
                .collect(Collectors.toList());
    }

    private boolean confirmUserExists(Optional<User> oUser) {
        if (!oUser.isPresent())
            return false;
        return true;
    }
}
