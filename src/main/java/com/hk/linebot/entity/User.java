package com.hk.linebot.entity;

import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;

    private String userName;

    @Indexed(unique = true)
    private String lineToken;

    private boolean enabled = true;

    private String meta;

    @CreatedDate
    @Timestamp
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Timestamp
    private LocalDateTime updatedTime;

    private List<Message> messages;
}
