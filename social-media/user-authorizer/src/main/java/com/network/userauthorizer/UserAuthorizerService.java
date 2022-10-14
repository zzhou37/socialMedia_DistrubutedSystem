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
        String inputPassword = userInfo.password();
        List<MediaUser> realUser = userRepository.findByUsername(userInfo.username());
        // if more than one user been found
        if(realUser.size() > 1) {
            return new UserAuthorization(userInfo.username(), userInfo.email(), "moreThenOneUserFound");
        }
        String realPassword = realUser.get(0).getPassword();
        //compare password
        if(inputPassword.compareTo(realPassword) == 0){
            return new UserAuthorization(userInfo.username(), userInfo.email(), "accept");
        }
        else{
            return new UserAuthorization(userInfo.username(), userInfo.email(), "reject");
        }
    }

    public UserAuthorization registerUser(UserInfo userInfo){
        // check if the user already exist
        if(userRepository.findByUsername(userInfo.username()).size() > 0){
            return new UserAuthorization(userInfo.username(), userInfo.email(), "alreadyExist");
        }
        MediaUser newUser = MediaUser.builder()
                .username(userInfo.username())
                .password(userInfo.password())
                .email(userInfo.email())
                .build();
        userRepository.saveAndFlush(newUser);
        return new UserAuthorization(userInfo.username(), userInfo.email(), "success");
    }
}

