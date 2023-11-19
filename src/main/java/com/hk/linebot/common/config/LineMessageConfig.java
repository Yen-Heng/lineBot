package com.hk.linebot.common.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class LineMessageConfig {

    public static LineMessageConfig INSTANCE;

    @PostConstruct
    public void init() {
        INSTANCE = this;
    }

    @Value("${line.bot.channel-secret}")
    private String channelSecret;

    @Value("${line.bot.channel-token}")
    private String channelToken;
}
