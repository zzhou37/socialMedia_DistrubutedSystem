package com.network.clients.friendservice;

import com.network.clients.userauthorizer.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "friend-service")
public interface FriendClient {
    @PostMapping("/is-friend")
    FriendStatue isFriend(@RequestBody UserPair userPair);

    @PostMapping("/get-friends")
    FriendList getFriends(@RequestBody UserInfo userInfo);
    @PostMapping("/add-friend")
    FriendStatue addFriend(@RequestBody UserPair userPair);
}
