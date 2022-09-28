package com.socialmedia.contentservice;

import com.socialmedia.clients.friendservice.FriendServiceClient;
import com.socialmedia.clients.friendservice.FriendStatue;
import com.socialmedia.clients.friendservice.UserPair;
import com.socialmedia.clients.userauthorizer.UserAuthorization;
import com.socialmedia.clients.userauthorizer.UserAuthorizerClient;
import com.socialmedia.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContentServiceService {

    UserAuthorizerClient userAuthorizerClient;
    FriendServiceClient friendServiceClient;
    public String getConversation(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.userName(), userPair.password(),userPair.email());
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
