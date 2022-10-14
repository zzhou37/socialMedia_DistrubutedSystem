package com.network.friend;

import com.network.clients.friendservice.FriendList;
import com.network.clients.friendservice.FriendStatue;
import com.network.clients.friendservice.UserPair;
import com.network.clients.userauthorizer.UserAuthorization;
import com.network.clients.userauthorizer.UserAuthorizerClient;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FriendService {
    private final UserAuthorizerClient userAuthorizerClient;
    private final FriendRepository friendRepository;
    public FriendList getFriends (UserInfo userInfo){
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept") == 0){
            Friend friendOfUser = friendRepository.findByUserName(userInfo.userName());
            return new FriendList(userInfo.userName(),
                    userInfo.email(),
                    friendOfUser.getFriendList(),
                    friendOfUser.getFriendWaitList());
        }
        else{
            return new FriendList(userInfo.userName(), userInfo.email(), null, null);
        }
    }

    public FriendStatue isFriend(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.userName(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if (authorization.statue().compareTo("accept") == 0){
            boolean isFriendOfUser = friendRepository.findByUserName(userInfo.userName())
                    .getFriendList()
                    .contains(userPair.userName1());
            System.out.println(isFriendOfUser);
            if(isFriendOfUser){
                return new FriendStatue(userPair.userName(), userPair.email(), userPair.userName1(), "isFriend");
            }
            else{
                return new FriendStatue(userPair.userName(), userPair.email(), userPair.userName1(), "notFriend");
            }

        }
        else{
            return new FriendStatue(userPair.userName(), userPair.email(), userPair.userName1(), "notAuthorizedUser");
        }
    }

    public FriendStatue addFriend(UserPair userPair){
        return null;
    }
}
