package com.codenaren.hashtag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashtagApplication {
    public static void main(String[] args) {
        SpringApplication.run(
                HashtagApplication.class, args);
    }
}
//    @Bean
//    CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
//                                        PasswordEncoder encoder) {
//        return args -> {
//            for (int i = 0; i < 1; i++) {
//                Faker faker = new Faker();
//                Name name = faker.name();
//                String firstName = name.firstName().toLowerCase();
//                String lastName = name.lastName().toLowerCase();
//                Gender gender = Gender.getRandomGender();
//                var password = encoder.encode(faker.internet().password(8
//                        , 16
//                        , true
//                        , true
//                        , true));
//                Customer customer = new Customer(
//                        name.username(),
//                        firstName,
//                        lastName,
//                        firstName + "." + lastName + "@codeNaren.com",
//                        password,
//                        gender,
//                        faker.random().nextInt(18, 40)
//                );
////                customerRepository.save(customer);
//
//            }
//        };