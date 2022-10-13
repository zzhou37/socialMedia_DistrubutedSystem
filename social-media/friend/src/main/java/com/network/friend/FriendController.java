package com.network.friend;

import com.network.clients.friendservice.FriendList;
import com.network.clients.friendservice.FriendStatue;
import com.network.clients.friendservice.UserPair;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/friend")
@AllArgsConstructor
public class FriendController {

    FriendService friendService;

    @PostMapping("/get-friends")
    public FriendList getFriends(@RequestBody UserInfo userInfo){
        log.info("get friends of user");
        return friendService.getFriends(userInfo);
    }

    @PostMapping("/is-friend")
    public FriendStatue isFriend(@RequestBody UserPair userPair){
        log.info("check is user1 and user2 are friends");
        return friendService.isFriend(userPair);
    }
}

