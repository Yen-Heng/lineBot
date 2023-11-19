package com.hk.linebot.dao;

import com.hk.linebot.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByIdOrUserName(String id, String userName);

    Optional<User> findByLineToken(String lineToken);
}
