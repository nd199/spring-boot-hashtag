package com.codenaren.hashtag;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Entity.Gender;
import com.codenaren.hashtag.Repository.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HashtagApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                HashtagApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
                                        PasswordEncoder encoder) {
        return args -> {
            for (int i = 0; i < 1; i++) {
                Faker faker = new Faker();
                Name name = faker.name();
                String firstName = name.firstName().toLowerCase();
                String lastName = name.lastName().toLowerCase();
                Gender gender = Gender.getRandomGender();
                var password = encoder.encode(faker.internet().password(8
                        , 16
                        , true
                        , true
                        , true));
                String email = firstName + "." + lastName + "@codeNaren.com";
                Customer customer = new Customer(
                        name.username(),
                        firstName,
                        lastName,
                        email,
                        password,
                        gender,
                        faker.random().nextInt(18, 40)
                );
                customerRepository.save(customer);
                System.out.println(email);
                System.out.println(password);
            }
        };
    }
}