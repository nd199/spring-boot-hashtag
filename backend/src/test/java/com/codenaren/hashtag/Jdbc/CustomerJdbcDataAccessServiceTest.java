package com.codenaren.hashtag.Jdbc;

import com.codenaren.hashtag.AbstractTestContainers;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Entity.Gender;
import com.codenaren.hashtag.Utils.CustomerRowMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJdbcDataAccessServiceTest extends AbstractTestContainers {

    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();
    private CustomerJdbcDataAccessService service;

    @BeforeEach
    void setUp() {
        service = new CustomerJdbcDataAccessService(
                customerRowMapper,
                getJdbcTemplate()
        );
    }

    @Test
    void getAllCustomers() {

        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );

        service.registerCustomer(customer);

        List<Customer> actual = service.getAllCustomers();

        assertThat(actual).isNotEmpty();
    }

    @Test
    void getByCustomerId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );
        service.registerCustomer(customer);

        Long id = service.getAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        Optional<Customer> actual = service.getByCustomerId(id);

        assertThat(actual).isPresent()
                .hasValueSatisfying(
                        c -> {
                            assertThat(c.getId()).isEqualTo(id);
                            assertThat(c.getFirstName()).isEqualTo(customer.getFirstName());
                            assertThat(c.getLastName()).isEqualTo(customer.getLastName());
                            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                            assertThat(c.getPassword()).isEqualTo(customer.getPassword());
                            assertThat(c.getGender()).isEqualTo(customer.getGender());
                            assertThat(c.getAge()).isEqualTo(customer.getAge());
                        }
                );
    }

}