package com.network.clients.userauthorizer;


public record UserInfo(
        String userName,
        String passWord,
        String email
) {

}
