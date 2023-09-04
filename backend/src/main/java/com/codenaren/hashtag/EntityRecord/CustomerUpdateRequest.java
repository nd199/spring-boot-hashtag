package com.codenaren.hashtag.EntityRecord;

public record CustomerUpdateRequest(
        String userName,
        String firstName,
        String lastName,
        String email,
        String password,
        String gender,
        Integer age
) {
}
