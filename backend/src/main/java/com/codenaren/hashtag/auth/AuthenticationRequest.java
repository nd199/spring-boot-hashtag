package com.codenaren.hashtag.auth;

public record AuthenticationRequest(
        String userName,
        String password
) {
}
