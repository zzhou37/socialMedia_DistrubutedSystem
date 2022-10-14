package com.network.friend;

import com.network.clients.friendservice.*;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class FriendController {

    FriendService friendService;

    @PostMapping("/friend/get-friends")
    public FriendList getFriends(@RequestBody UserInfo userInfo){
        log.info("get friends of user");
        return friendService.getFriends(userInfo);
    }

    @PostMapping("/friend/is-friend")
    public FriendStatue isFriend(@RequestBody UserPair userPair){
        log.info("check is user1 and user2 are friends");
        return friendService.isFriend(userPair);
    }

    @PostMapping("/friend/request-friend")
    public FriendStatue requestFriend(@RequestBody UserPair userPair){
        log.info("user request user1 as friend");
        return friendService.requestFriend(userPair);
    }

    @PostMapping("/friend/initalize")
    public CreateStatue createUserFriendProfile(@RequestBody UserInfo userInfo){
        log.info("creat a blank user profile for user");
        return friendService.createUserFriendProfile(userInfo);
    }
}

