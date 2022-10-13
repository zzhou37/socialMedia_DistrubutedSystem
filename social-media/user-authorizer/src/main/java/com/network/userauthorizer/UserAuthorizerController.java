package com.network.userauthorizer;

import com.network.clients.userauthorizer.UserAuthorization;
import com.network.clients.userauthorizer.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/user-authorizer")
public class UserAuthorizerController {

    UserAuthorizerService userAuthorizerService;

    @GetMapping("/check-alive")
    public String greeting(){
        return "at least one userAuthorizerService is up";
    }

    @PostMapping("/user/check-authorization")
    public UserAuthorization checkUser(@RequestBody UserInfo userInfo){
        log.info("check if user is authorized");
        return userAuthorizerService.checkUserInfo(userInfo);
    }

    @PostMapping("/user/register-user")
    public UserAuthorization registerUser(@RequestBody UserInfo userInfo){
        log.info("creat a new user");
        return userAuthorizerService.registerUser(userInfo);
    }
}
