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
                return respondFriendStatue(userPair, "notAvailable");
            }
            boolean isFriendOfUser = userProfile
                                    .getFriendList()
                                    .contains(userPair.username1());
            if(isFriendOfUser){
                return respondFriendStatue(userPair, "isFriend");
            }
            else{
                return respondFriendStatue(userPair, "notFriend");
            }

        }
        else{
            return respondFriendStatue(userPair, "notAuthorized");
        }
    }

    public FriendStatue requestFriend(UserPair userPair){
        UserInfo userInfo = new UserInfo(userPair.username(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept")==0){
            // find user profile, add user1 to friend waitList and save back to database
            Friend friendOfUser1 = friendRepository.findByUsername(userPair.username1());
            if(friendOfUser1 == null){
                return respondFriendStatue(userPair, "notAvailable");
            }
            List<String> friendWaitList = friendOfUser1.getFriendWaitList();
            List<String> friendList = friendOfUser1.getFriendList();
            if(friendList.contains(userPair.username())){
                return respondFriendStatue(userPair, "alreadyFriend");
            }
            else if(friendWaitList.contains(userPair.username())){
                return respondFriendStatue(userPair, "alreadySent");
            }
            else{
                friendWaitList.add(userPair.username());
                friendOfUser1.setFriendWaitList(friendWaitList);
                friendRepository.save(friendOfUser1);
                return respondFriendStatue(userPair, "success");
            }
        }
        else{
            return respondFriendStatue(userPair, "notAuthorized");
        }
    }

    public FriendStatue addFriend(UserPair userPair){
        //check if user is authorized
        UserInfo userInfo = new UserInfo(userPair.username(), userPair.password(), userPair.email());
        UserAuthorization authorization = userAuthorizerClient.checkUser(userInfo);
        if(authorization.statue().compareTo("accept")==0){
            if(!moveFromWaitListToFriendList(userPair.username(), userPair.username1())){
                return respondFriendStatue(userPair, "notAvailable");
            }
            if(!addToFriendList(userPair.username1(), userPair.username())){
                return respondFriendStatue(userPair, "notAvailable");
            }
            return respondFriendStatue(userPair, "success");
        }
        else{
            return respondFriendStatue(userPair, "notAuthorized");
        }
    }

    private boolean moveFromWaitListToFriendList(String user, String user1){
        //move user1 from user's friend wait list to friend list
        Friend friendOfUser = friendRepository.findByUsername(user);
        if(friendOfUser == null){
            return false;
        }
        List<String> friendWaitList = friendOfUser.getFriendWaitList();
        List<String> friendList = friendOfUser.getFriendList();
        if(friendWaitList.contains(user1)){
            friendWaitList.remove(user1);
            friendList.add(user1);
            friendOfUser.setFriendList(friendList);
            friendOfUser.setFriendWaitList(friendWaitList);
            friendRepository.save(friendOfUser);
            return true;
        }
        else{
            return false;
        }
    }

    private boolean addToFriendList(String user, String user1){
        Friend friendOfUser = friendRepository.findByUsername(user);
        if(friendOfUser == null){
            return false;
        }
        List<String> friendList = friendOfUser.getFriendList();
        if(!friendList.contains(user1)){
            friendList.add(user1);
            friendOfUser.setFriendList(friendList);
            friendRepository.save(friendOfUser);
            return true;
        }
        else{
            return false;
        }
    }

    private FriendStatue respondFriendStatue(UserPair userPair, String statue){
        return new FriendStatue(userPair.username(), userPair.email(), userPair.username1(), statue);
    }
}
