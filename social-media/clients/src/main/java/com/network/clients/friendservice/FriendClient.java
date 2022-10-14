package com.network.clients.friendservice;

import com.network.clients.userauthorizer.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "friend-service")
public interface FriendClient {
    @PostMapping("/friend/is-friend")
    FriendStatue isFriend(@RequestBody UserPair userPair);

    @PostMapping("/friend/get-friends")
    FriendList getFriends(@RequestBody UserInfo userInfo);
    @PostMapping("/friend/add-friend")
    FriendStatue addFriend(@RequestBody UserPair userPair);
}
