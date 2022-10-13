package com.network.userauthorizer;

import com.network.clients.userauthorizer.UserAuthorization;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAuthorizerService {

    private final UserRepository userRepository;
    public UserAuthorization checkUserInfo(UserInfo userInfo){
        String inputPassword = userInfo.passWord();
        List<MediaUser> realUser = userRepository.findByUserName(userInfo.userName());
        // if more than one user been found
        if(realUser.size() > 1) {
            return new UserAuthorization(userInfo.userName(), userInfo.email(), "moreThenOneUserFound");
        }
        String realPassword = realUser.get(0).getPassWord();
        //compare password
        if(inputPassword.compareTo(realPassword) == 0){
            return new UserAuthorization(userInfo.userName(), userInfo.email(), "accept");
        }
        else{
            return new UserAuthorization(userInfo.userName(), userInfo.email(), "reject");
        }
    }

    public UserAuthorization registerUser(UserInfo userInfo){
        // check if the user already exist
        if(userRepository.findByUserName(userInfo.userName()).size() > 0){
            return new UserAuthorization(userInfo.userName(), userInfo.email(), "alreadyExist");
        }
        MediaUser newUser = MediaUser.builder()
                .userName(userInfo.userName())
                .passWord(userInfo.passWord())
                .email(userInfo.email())
                .build();
        userRepository.saveAndFlush(newUser);
        return new UserAuthorization(userInfo.userName(), userInfo.email(), "success");
    }
}

