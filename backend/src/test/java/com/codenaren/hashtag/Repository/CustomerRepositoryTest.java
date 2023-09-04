package com.codenaren.hashtag.Repository;

import com.codenaren.hashtag.AbstractTestContainers;
import com.codenaren.hashtag.Entity.Customer;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    private static final Faker FAKER = new Faker();
    @Autowired
    private CustomerRepository underTest;
    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setUp() {
        context.getBeanDefinitionCount();
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }

    @Test
    void testExistsByUserName() {
        //Given
        String username = FAKER.name().username();
        Customer customer = new Customer(
                username,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        underTest.save(customer);
        //When
        var actual = underTest.existsByUserName(username);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void testExistsByUserNameFailsWhenUserNameNotExist() {
        //Given
        String username = FAKER.name().username();
        //When
        var actual = underTest.existsByUserName(username);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void testExistsByEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String username = FAKER.name().username();
        Customer customer = new Customer(
                username,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        underTest.save(customer);
        //When
        var actual = underTest.existsByEmail(email);
        //Then
        assertThat(actual).isTrue();
    }


    @Test
    void testExistsByEmailFailsWhenEmailNotPresent() {
        //Given

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        //When
        var actual = underTest.existsByEmail(email);
        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void testFindByUserName() {
        //Given
        String username = FAKER.name().username();
        Customer customer = new Customer(
                username,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        underTest.save(customer);
        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getUserName().equals(username))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        var actual = underTest.findById(id);
        //Then
        assertThat(actual).hasValueSatisfying(
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

    @Test
    void testDeleteByUserNameAndEmail() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String username = FAKER.name().username();
        Customer customer = new Customer(
                username,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        underTest.save(customer);
        //When
        underTest.deleteByUserNameAndEmail(username, email);
        //Checking
        Boolean existsByEmail = underTest.existsByEmail(email);
        Boolean existsByUserName = underTest.existsByUserName(username);
        //Then
        assertThat(existsByEmail).isFalse();
        //Fails when given true as Customer is already removed/deleted with userName
        assertThat(existsByUserName).isFalse();
    }

    @Test
    void getCustomersById() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );

        underTest.save(customer);

        Long id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When

        Optional<Customer> actual = underTest.getCustomerById(id);

        //Then
        assertThat(actual).isPresent().hasValueSatisfying(
                c -> {
                    assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getFirstName()).isEqualTo(customer.getFirstName());
                    assertThat(c.getLastName()).isEqualTo(customer.getLastName());
                    assertThat(c.getEmail()).isEqualTo(customer.getEmail());
                    assertThat(c.getPassword()).isEqualTo(customer.getPassword());
                    assertThat(c.getGender()).isEqualTo(customer.getGender());
                    assertThat(c.getAge()).isEqualTo(customer.getAge());
                });
    }

    @Test
    void getCustomersByIdFailsWhenIdNotExist() {
        //Given
        Long id = -1L;
        //When
        Optional<Customer> actual = underTest.getCustomerById(id);
        //Then
        assertThat(actual).isEmpty();

    }
}