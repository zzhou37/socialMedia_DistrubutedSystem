package com.network.friend;

import com.network.clients.friendservice.CreateStatue;
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

    public CreateStatue createUserFriendProfile(UserInfo userInfo){
        Friend friendOfUser = friendRepository.findByUsername(userInfo.username());
        if(friendOfUser == null){
            Friend newUser = Friend.builder()
                    .username(userInfo.username())
                    .email(userInfo.email())
                    .friendList(new ArrayList<>())
                    .friendWaitList(new ArrayList<>())
                    .build();
            friendRepository.save(newUser);
            return new CreateStatue(userInfo.username(), userInfo.email(), "success");
        }
        else{
            return new CreateStatue(userInfo.username(), userInfo.email(), "alreadyExist");
        }
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
            Friend userProfile = friendRepository.findByUsername(userInfo.username());
            if(userProfile == null){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notAvailable");
            }
            boolean isFriendOfUser = userProfile
                                    .getFriendList()
                                    .contains(userPair.username1());
            if(isFriendOfUser){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "isFriend");
            }
            else{
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notFriend");
            }

        }
        else{
            return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notAuthorized");
        }
    }

    public FriendStatue requestFriend(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.username(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept")==0){
            // find user profile, add user1 to friend waitList and save back to database
            Friend friendOfUser1 = friendRepository.findByUsername(userPair.username1());
            if(friendOfUser1 == null){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notAvailable");
            }
            List<String> friendWaitList = friendOfUser1.getFriendWaitList();
            List<String> friendList = friendOfUser1.getFriendList();
            if(friendList.contains(userPair.username())){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "alreadyFriend");
            }
            else if(friendWaitList.contains(userPair.username())){
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "alreadySent");
            }
            else{
                friendWaitList.add(userPair.username());
                friendOfUser1.setFriendWaitList(friendWaitList);
                friendRepository.save(friendOfUser1);
                return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "success");
            }
        }
        else{
            return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), "notAuthorized");
        }
    }

    public FriendStatue addFriend(UserPair userPair){
        return null;
    }
}
