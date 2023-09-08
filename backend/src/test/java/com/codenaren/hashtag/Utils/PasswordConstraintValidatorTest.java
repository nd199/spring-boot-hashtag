package com.codenaren.hashtag.Utils;

import org.junit.jupiter.api.Test;
import org.passay.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordConstraintValidatorTest {

    @Test
    void isValid() {

        //Given
        String password = "A1@dasdasdas";

        PasswordData data = new PasswordData(password);
        //When
        PasswordValidator validator =
                new PasswordValidator(Arrays.asList(
                        new LengthRule(8, 16),
                        new CharacterRule(
                                EnglishCharacterData.UpperCase, 1
                        ),

                        new CharacterRule(
                                EnglishCharacterData.LowerCase, 1
                        ),

                        new UsernameRule(),

                        new CharacterRule(
                                EnglishCharacterData.Special, 1
                        ),

                        new CharacterRule(
                                EnglishCharacterData.Digit, 1
                        ),
                        new WhitespaceRule()

                ));
        RuleResult result = validator.validate(data);
        assertThat(result.isValid()).isTrue();
    }

    @Test
    void isNotValid() {

        //Given
        String password = "asdasdas";

        PasswordData data = new PasswordData(password);
        //When
        PasswordValidator validator =
                new PasswordValidator(Arrays.asList(
                        new LengthRule(8, 16),
                        new CharacterRule(
                                EnglishCharacterData.UpperCase, 1
                        ),

                        new CharacterRule(
                                EnglishCharacterData.LowerCase, 1
                        ),

                        new UsernameRule(),

                        new CharacterRule(
                                EnglishCharacterData.Special, 1
                        ),

                        new CharacterRule(
                                EnglishCharacterData.Digit, 1
                        ),
                        new WhitespaceRule()

                ));
        RuleResult result = validator.validate(data);
        assertThat(result.isValid()).isFalse();
    }
}