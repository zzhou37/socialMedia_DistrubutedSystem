package com.network.clients.friendservice;

public record CreateStatue(
        String username,
        String email,
        String statue
) {
}
