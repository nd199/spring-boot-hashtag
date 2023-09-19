package com.codenaren.hashtag.complete;


import com.codenaren.hashtag.Dto.CustomerDTO;
import com.codenaren.hashtag.Entity.Gender;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    public static String URL = "/api/v1/customers";
    //create Reg Request
    //send a post Request
    //get all customers
    //make sure that customer is present
    // get customer by id
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void canRegisterACustomer() {

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

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age
        );

        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request), CustomerRegistrationRequest.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)
                        .getResponseHeaders()
                        .get(AUTHORIZATION))
                .get(0);


        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id).findFirst()
                .orElseThrow();

        //make sure that customer is present
        CustomerDTO expectedCustomer = new CustomerDTO(
                id,
                userName,
                firstName,
                lastName,
                email,
                age,
                gender,
                List.of("ROLE_USER")
        );


        assertThat(allCustomers).contains(expectedCustomer);

        // get customer by id;
        webTestClient.get()
                .uri(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>() {
                }).isEqualTo(expectedCustomer);
    }


    @Test
    void deleteACustomer() {
        Faker faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        String fakerName = firstName + lastName;
        String userName = fakerName + UUID.randomUUID();
        String userName2 = fakerName + UUID.randomUUID();
        String password = passwordEncoder.encode(faker.internet().password());
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        String email2 = firstName + UUID.randomUUID() + "@codeNaren.com";
        Gender gender = Gender.getRandomGender();
        int age = faker.random().nextInt(15, 100);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age
        );

        CustomerRegistrationRequest request2 = new CustomerRegistrationRequest(
                userName2, firstName, lastName, email2, password, gender, age
        );
        //posting Customer 1
        webTestClient.post()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        // posting customer 2
        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request2), CustomerRegistrationRequest.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)
                        .getResponseHeaders()
                        .get(AUTHORIZATION))
                .get(0);

        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id).findFirst()
                .orElseThrow();

        //delete a customer1
        webTestClient.delete()
                .uri(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        // get customer1 by id;
        webTestClient.get()
                .uri(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void updateACustomer() {

        Faker faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        String fakerName = firstName + lastName;
        String userName = fakerName + UUID.randomUUID();
        String password = passwordEncoder.encode(faker.internet().password());
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        Gender gender = Gender.getRandomGender();
        int age = faker.random().nextInt(15, 100);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age);

        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request), CustomerRegistrationRequest.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)
                        .getResponseHeaders()
                        .get(AUTHORIZATION))
                .get(0);

        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(URL)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.email().equals(email))
                .map(CustomerDTO::id).findFirst()
                .orElseThrow();


        String newName = name.firstName();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newName, null, null, null, null, null
        );

        webTestClient.put()
                .uri(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        CustomerDTO updatedCustomer = webTestClient.get()
                .uri(URL + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        CustomerDTO expectedCustomer = new CustomerDTO(
                id,
                userName,
                newName,
                lastName,
                email,
                age,
                gender,
                List.of("ROLE_USER")
        );

        assertThat(updatedCustomer).isEqualTo(expectedCustomer);
    }
}
