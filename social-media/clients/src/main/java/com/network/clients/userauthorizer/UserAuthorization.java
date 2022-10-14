package com.network.clients.userauthorizer;

public record UserAuthorization(
        String username,
        String email,
        String statue
) {
}
