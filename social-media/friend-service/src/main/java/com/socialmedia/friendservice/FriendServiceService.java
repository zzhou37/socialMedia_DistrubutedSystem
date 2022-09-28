package com.socialmedia.friendservice;

import com.socialmedia.clients.friendservice.FriendList;
import com.socialmedia.clients.friendservice.FriendStatue;
import com.socialmedia.clients.friendservice.UserPair;
import com.socialmedia.clients.userauthorizer.UserAuthorization;
import com.socialmedia.clients.userauthorizer.UserAuthorizerClient;
import com.socialmedia.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendServiceService {
    private final UserAuthorizerClient userAuthorizerClient;
    public FriendList getFriends (UserInfo userInfo){
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept") == 0){
            return new FriendList(userInfo.userName(), userInfo.email(), "lai,yibo");
        }
        else{
            return new FriendList(userInfo.userName(), userInfo.email(), "notAuthorizedUser");
        }
    }

    public FriendStatue isFriend(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.userName(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if (authorization.statue().compareTo("success") == 0){
            return new FriendStatue(userPair.userName(), userPair.email(), userPair.userName1(), "isFriends");
        }
        else{
            return new FriendStatue(userPair.userName(), userPair.email(), userPair.userName1(), "notAuthorizedUser");
        }
    }
}
