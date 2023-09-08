package com.codenaren.hashtag.Entity;

import java.util.Random;

public enum Gender {
    MALE,
    FEMALE;

    private static final Random RANDOM = new Random();

    public static Gender getRandomGender() {
        Gender[] genders = Gender.values();
        return genders[RANDOM.nextInt(genders.length)];
    }
}
