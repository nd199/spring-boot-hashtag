package com.codenaren.hashtag;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Repository.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class  HashtagApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                HashtagApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository) {
        return args -> {
            for (int i = 0; i < 20; i++) {
                Faker faker = new Faker();
                Name name = faker.name();
                String firstName = name.firstName().toLowerCase();
                String lastName = name.lastName().toLowerCase();
                var password = faker.internet().password(8
                        , 16
                        , true
                        , true
                        , true);
                Customer customer = new Customer(
                        name.username(), firstName,
                        lastName, firstName + "." + lastName +
                                  faker.random().nextInt(1, 9999) +
                                  "@codeNaren.com",
                        password,
                        faker.dog().gender(),
                        faker.random().nextInt(18, 99));
                customerRepository.save(customer);
            }

        };

    }
}