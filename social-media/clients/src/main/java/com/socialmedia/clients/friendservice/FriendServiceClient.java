package com.socialmedia.clients.friendservice;

import com.socialmedia.clients.userauthorizer.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "friend-service")
public interface FriendServiceClient {
    @PostMapping("/is-friend")
    FriendStatue isFriend(@RequestBody UserPair userPair);

    @PostMapping("/get-friends")
    FriendList getFriends(@RequestBody UserInfo userInfo);
}
