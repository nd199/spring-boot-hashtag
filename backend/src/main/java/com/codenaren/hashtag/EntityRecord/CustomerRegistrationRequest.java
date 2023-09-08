package com.codenaren.hashtag.EntityRecord;

import com.codenaren.hashtag.Entity.Gender;

public record CustomerRegistrationRequest(
        String userName,
        String firstName,
        String lastName,
        String email,
        String password,
        Gender gender,
        Integer age
) {
}
