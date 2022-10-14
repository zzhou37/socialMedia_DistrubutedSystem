package com.network.friend;

import com.network.clients.friendservice.FriendList;
import com.network.clients.friendservice.FriendStatue;
import com.network.clients.friendservice.UserPair;
import com.network.clients.userauthorizer.UserAuthorization;
import com.network.clients.userauthorizer.UserAuthorizerClient;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final UserAuthorizerClient userAuthorizerClient;
    private final FriendRepository friendRepository;

    private void injectOneExample(){
            List<String> friendList = new ArrayList<String>();
            friendList.add("lai");
            friendList.add("yibo");
            List<String> friendWaitList = new ArrayList<String>();
            friendWaitList.add("kai");
            friendWaitList.add("someone");
            Friend friendOfZhou = Friend.builder()
            .username("zhou")
            .email("zhou@gmail.com")
            .friendList(friendList).friendWaitList(friendWaitList)
            .build();
            friendRepository.save(friendOfZhou);
    }
    public FriendList getFriends (UserInfo userInfo){
        //injectOneExample();
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept") == 0){
            Friend friendOfUser = friendRepository.findByUsername(userInfo.username());
            return new FriendList(userInfo.username(),
                    userInfo.email(),
                    friendOfUser.getFriendList(),
                    friendOfUser.getFriendWaitList());
        }
        else{
            return new FriendList(userInfo.username(), userInfo.email(), null, null);
        }
    }

    public FriendStatue isFriend(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.username(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if (authorization.statue().compareTo("accept") == 0){
            boolean isFriendOfUser = friendRepository.findByUsername(userInfo.username())
                    .getFriendList()
                    .contains(userPair.username1());
            System.out.println(isFriendOfUser);
            if(isFriendOfUser){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "isFriend");
            }
            else{
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notFriend");
            }

        }
        else{
            return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notAuthorizedUser");
        }
    }

    public FriendStatue addFriend(UserPair userPair){
        return null;
    }
}
