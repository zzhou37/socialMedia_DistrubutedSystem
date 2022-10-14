package com.network.friend;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FriendRepository extends MongoRepository<Friend, String> {
    Friend findByUsername(String userName);
}
