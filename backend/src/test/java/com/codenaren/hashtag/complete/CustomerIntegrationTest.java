package com.codenaren.hashtag.complete;


import com.codenaren.hashtag.Entity.Customer;
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
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    public static String API = "/api/v1/";
    //create Reg Request
    //send a post Request
    //get all customers
    //make sure that customer is present
    // get customer by id
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canRegisterACustomer() {

        Faker faker = new Faker();
        Name name = faker.name();
        String firstName = name.firstName();
        String lastName = name.lastName();
        String fakerName = firstName + lastName;
        String userName = fakerName + UUID.randomUUID();
        String password = faker.internet().password();
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        int age = faker.random().nextInt(15, 100);
        Gender gender = Gender.getRandomGender();

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age
        );

        webTestClient.post()
                .uri(API + "addCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(API + "getAllCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();
        //make sure that customer is present
        Customer expectedCustomer = new Customer(
                userName, firstName, lastName, email, password, gender, age
        );
        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId).findFirst()
                .orElseThrow();

        expectedCustomer.setId(id);

        // get customer by id;
        webTestClient.get()
                .uri(API + "getCustomer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
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
        String password = faker.internet().password();
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        Gender gender = Gender.getRandomGender();
        int age = faker.random().nextInt(15, 100);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age
        );

        webTestClient.post()
                .uri(API + "addCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(API + "getAllCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId).findFirst()
                .orElseThrow();

        //delete a customer
        webTestClient.delete()
                .uri(API + "deleteCustomer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        // get customer by id;
        webTestClient.get()
                .uri(API + "getCustomer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
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
        String password = faker.internet().password();
        String email = lastName + UUID.randomUUID() + "@codeNaren.com";
        Gender gender = Gender.getRandomGender();
        int age = faker.random().nextInt(15, 100);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, firstName, lastName, email, password, gender, age);

        webTestClient.post()
                .uri(API + "/addCustomer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        List<Customer> allCustomers = webTestClient.get()
                .uri(API + "getAllCustomers")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        assert allCustomers != null;
        long id = allCustomers.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId).findFirst()
                .orElseThrow();

        int age1 = faker.random().nextInt(15, 99);
        String password1 = faker.internet().password();
        String email1 = faker.internet().emailAddress();
        String lastName1 = name.lastName();
        String firstName1 = name.firstName();
        Gender gender1 = Gender.getRandomGender();
        String userName1 = name.username();

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                userName1, firstName1, lastName1, email1, password1, gender1, age1
        );

        webTestClient.put()
                .uri(API + "updateCustomer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        Customer updatedCustomer = webTestClient.get()
                .uri(API + "getCustomer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expectedCustomer = new Customer(
                id, userName1, firstName1, lastName1, email1, password1, gender1, age1
        );

        assertThat(updatedCustomer).isEqualTo(expectedCustomer);
    }
}
