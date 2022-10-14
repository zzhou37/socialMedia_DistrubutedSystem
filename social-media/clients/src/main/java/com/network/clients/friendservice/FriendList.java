package com.network.clients.friendservice;

import java.util.List;

public record FriendList(
        String userName,
        String email,
        List<String> friendList,
        List<String> friendWaitList
) {
}