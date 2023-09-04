package com.codenaren.hashtag.EntityRecord;

public record CustomerRegistrationRequest(
        String userName,
        String firstName,
        String lastName,
        String email,
        String password,
        Integer age,
        String gender
) {
}
