package com.network.content;

import com.network.clients.friendservice.FriendClient;
import com.network.clients.friendservice.FriendStatue;
import com.network.clients.friendservice.UserPair;
import com.network.clients.userauthorizer.UserAuthorization;
import com.network.clients.userauthorizer.UserAuthorizerClient;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;

@Service
@AllArgsConstructor
public class ContentService {

    private final UserAuthorizerClient userAuthorizerClient;
    private final FriendClient friendServiceClient;
    public String getConversation(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.username(), userPair.password(),userPair.email());
        UserAuthorization isUserAuthorized = userAuthorizerClient.checkUser(userInfo);
        FriendStatue friendStatue = friendServiceClient.isFriend(userPair);
        if(true){
            return "Hello Zhou";
        }
        else{
            return "Not authorized user";
        }
    }
}
