package com.codenaren.hashtag.Dto;

import com.codenaren.hashtag.Entity.Gender;

import java.util.List;

public record CustomerDTO(
        Long id,
        String userName,
        String firstName,
        String lastName,
        String email,
        Integer age,
        Gender gender,
        List<String> roles
) {
}
