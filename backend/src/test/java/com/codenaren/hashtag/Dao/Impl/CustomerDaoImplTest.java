package com.codenaren.hashtag.Dao.Impl;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Entity.Gender;
import com.codenaren.hashtag.Repository.CustomerRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


class CustomerDaoImplTest {

    private static final Faker FAKER = new Faker();
    private CustomerDaoImpl underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerDaoImpl(customerRepository);
    }

    @AfterEach
    void tearDown() {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void existsByEmail() {
        //Given
        String emailAddress = FAKER.internet().safeEmailAddress();
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                emailAddress,
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );
        customerRepository.save(customer);
        //When
        underTest.existsByEmail(emailAddress);
        //Then
        verify(customerRepository).existsByEmail(emailAddress);
    }

    @Test
    void existsByUserName() {
        String username = FAKER.name().username();
        Customer customer = new Customer(
                username,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress(),
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );
        customerRepository.save(customer);
        //When
        underTest.existsByUserName(username);
        //Then
        verify(customerRepository).existsByUserName(username);
    }

    @Test
    void registerCustomer() {
        //Given
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress(),
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );
        //When
        underTest.registerCustomer(customer);
        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void updateCustomer() {
        //Given
        String username = FAKER.name().username();
        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        String email = FAKER.internet().safeEmailAddress();
        String password = FAKER.internet().password();
        Gender gender = Gender.MALE;
        Integer age = FAKER.random().nextInt(18, 99);
        Customer customer = new Customer(
                username,
                firstName,
                lastName,
                email,
                password,
                gender,
                age
        );
        //When
        underTest.updateCustomer(customer);
        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void getAllCustomers() {
        //When
        underTest.getAllCustomers();
        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void findByUserName() {
        //Given
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                FAKER.internet().safeEmailAddress(),
                FAKER.internet().password(),
                Gender.MALE,
                FAKER.random().nextInt(18, 99)
        );
        //When
        customerRepository.save(customer);
        //When
        underTest.findCustomerByUserName(customer.getUserName());
        //Then
        verify(customerRepository).getCustomerByUserName(customer.getUserName());
    }

    @Test
    void getByCustomerId() {
        //Given
        long id = 2L;
        //When
        underTest.getByCustomerId(id);
        //Then
        verify(customerRepository)
                .findById(id);
    }

    @Test
    void existsCustomerById() {
        //Given
        Long id = 1L;
        //When
        underTest.existsCustomerById(id);
        //Then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void testRemoveCustomerById() {
        //Given
        Long id = 1L;
        //When
        underTest.removeCustomerById(id);
        //Then
        verify(customerRepository).deleteCustomerById(id);
    }
}