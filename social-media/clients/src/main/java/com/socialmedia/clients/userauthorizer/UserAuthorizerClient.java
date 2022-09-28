package com.socialmedia.clients.userauthorizer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-authorizer")
public interface UserAuthorizerClient {
    @PostMapping("/user/check-authorization")
    UserAuthorization checkUser(@RequestBody UserInfo userInfo);
}
