package com.network.clients.userauthorizer;


public record UserInfo(
        String username,
        String password,
        String email
) {

}
