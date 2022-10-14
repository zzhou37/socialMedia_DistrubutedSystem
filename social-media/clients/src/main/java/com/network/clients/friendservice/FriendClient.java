package com.network.clients.friendservice;


import com.network.clients.userauthorizer.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "friend-service")
public interface FriendClient {
    @PostMapping("/friend/is-friend")
    FriendStatue isFriend(@RequestBody UserPair userPair);

    @PostMapping("/friend/get-friends")
    FriendList getFriends(@RequestBody UserInfo userInfo);

    @PostMapping("/friend/add-friend")
    FriendStatue addFriend(@RequestBody UserPair userPair);

    @PostMapping("/friend/request-friend")
    FriendStatue requestFriend(@RequestBody UserPair userPair);

    @PostMapping("/friend/create-user-friendProfile")
    CreateStatue createUserFriendProfile(@RequestBody UserInfo userInfo);
}
