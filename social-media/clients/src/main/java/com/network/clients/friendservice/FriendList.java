package com.network.clients.friendservice;

public record FriendList(
        String userName,
        String email,
        String friendListStr
) {
}
