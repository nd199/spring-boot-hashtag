package com.codenaren.hashtag.complete;


import com.codenaren.hashtag.Dto.CustomerDTO;
import com.codenaren.hashtag.Entity.Gender;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.Security.Jwt.JWTUtil;
import com.codenaren.hashtag.auth.AuthenticationRequest;
import com.codenaren.hashtag.auth.AuthenticationResponse;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthIntegrationTest {

    public static String AUTH = "/api/v1/auth";
    public static String URL = "/api/v1/customers";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;

    @Test
    void CanLoginWithAuth() {
        //Given
        Faker faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        String fakerName = firstName + lastName;
        String userName = fakerName + UUID.randomUUID();
        String password = passwordEncoder.encode(faker.internet().password());
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        int age = faker.random().nextInt(15, 100);
        Gender gender = Gender.getRandomGender();

        CustomerRegistrationRequest regRequest = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age
        );

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                userName, password
        );

        //trying to login before registering;
        webTestClient.post()
                .uri(AUTH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        //registering a customer
        webTestClient.post()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(regRequest), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //trying to login after registering;
        EntityExchangeResult<AuthenticationResponse> entityResult = webTestClient.post()
                .uri(AUTH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();


        String jwtToken = Objects.requireNonNull(entityResult.getResponseHeaders().get(AUTHORIZATION)).get(0);

        //When
        AuthenticationResponse authResponse = entityResult.getResponseBody();

        CustomerDTO customerDTO = authResponse.customerDTO();

        //Then
        assertThat(jwtUtil.issueToken(jwtToken,
                customerDTO.userName()));
        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.firstName()).isEqualTo(firstName);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.lastName()).isEqualTo(lastName);
        assertThat(customerDTO.gender()).isEqualTo(gender);
        assertThat(customerDTO.roles()).isEqualTo(List.of("ROLE_USER"));
    }
}
