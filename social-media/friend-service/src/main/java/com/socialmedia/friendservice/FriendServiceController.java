package com.socialmedia.friendservice;

import com.socialmedia.clients.friendservice.FriendList;
import com.socialmedia.clients.friendservice.FriendStatue;
import com.socialmedia.clients.friendservice.UserPair;
import com.socialmedia.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@AllArgsConstructor
public class FriendServiceController {

    FriendServiceService friendServiceService;

    @PostMapping("/get-friends")
    public FriendList getFriends(@RequestBody UserInfo userInfo){
        log.info("get friends of user");
        return friendServiceService.getFriends(userInfo);
    }

    @PostMapping("/is-friend")
    public FriendStatue isFriend(@RequestBody UserPair userPair){
        log.info("check is user1 and user2 are friends");
        return friendServiceService.isFriend(userPair);
    }
}
