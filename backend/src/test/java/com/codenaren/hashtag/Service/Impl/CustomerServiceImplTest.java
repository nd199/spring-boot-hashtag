package com.codenaren.hashtag.Service.Impl;

import com.codenaren.hashtag.Dao.Impl.CustomerDaoImpl;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;
import com.codenaren.hashtag.Exceptions.ResourceNotFound;
import com.github.javafaker.Faker;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private static final Faker FAKER = new Faker();
    @Rule
    public MockitoRule rule;
    private CustomerServiceImpl underTest;
    @Mock
    private CustomerDaoImpl customerDao;

    @BeforeEach
    void setUp() {
        rule = MockitoJUnit.rule().strictness(Strictness.LENIENT);
        underTest = new CustomerServiceImpl(customerDao);
    }

    @Test
    void addCustomer() {

        String email = "lopez@codeNaren.com";
        //Given
        when(customerDao.existsByEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Keciaa12", "lopez", "1o",
                email, "poofi", 20, "female"
        );

        //When
        underTest.addCustomer(request);


        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).registerCustomer(customerArgumentCaptor.capture());

        Customer customerCaptured = customerArgumentCaptor.getValue();

        assertThat(customerCaptured.getId()).isNull();
        assertThat(customerCaptured.getUserName()).isEqualTo(request.userName());
        assertThat(customerCaptured.getFirstName()).isEqualTo(request.firstName());
        assertThat(customerCaptured.getLastName()).isEqualTo(request.lastName());
        assertThat(customerCaptured.getEmail()).isEqualTo(request.email());
        assertThat(customerCaptured.getPassword()).isEqualTo(request.password());
        assertThat(customerCaptured.getGender()).isEqualTo(request.gender());
        assertThat(customerCaptured.getAge()).isEqualTo(request.age());

    }


    @Test
    void getListOfCustomers() {
        //When
        underTest.getListOfCustomers();
        //Then
        verify(customerDao).getAllCustomers();
    }

    @Test
    void findCustomerByUserName() {
        //Given
        String userName = FAKER.name().username();
        String email = FAKER.internet().safeEmailAddress();
        Customer customer = new Customer(
                userName,
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        //When
        when(customerDao.findCustomerByUserName(userName)).thenReturn(Optional.of(customer));
        //Then
        Customer customerByUserName = underTest.findCustomerByUserName(userName);
        assertThat(customerByUserName).isEqualTo(customer);
    }


    @Test
    void updateCustomer() {
        //Given
        long id = 10L;
        //When
        Customer customer = new Customer(
                "ram1999", "Ram", "D", "r@gmail.com",
                "rmd@2022", "Male", 23
        );
        //Then
        when(customerDao.getByCustomerId(id))
                .thenReturn(Optional.of(customer));
        String email = "nmd@gmail.com";
        CustomerUpdateRequest updateRequest =
                new CustomerUpdateRequest(
                        "ram125", "Ram", "d", email,
                        "rm@2022hiAugust", "Male", 23
                );
        when(customerDao.existsByEmail(email)).thenReturn(false);

        underTest.updateCustomer(updateRequest, id);

        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(
                customerArgumentCaptor.capture()
        );
        Customer customerCaptured = customerArgumentCaptor.getValue();

        assertThat(customerCaptured.getId()).isNull();
        assertThat(customerCaptured.getUserName()).isEqualTo(updateRequest.userName());
        assertThat(customerCaptured.getFirstName()).isEqualTo(updateRequest.firstName());
        assertThat(customerCaptured.getLastName()).isEqualTo(updateRequest.lastName());
        assertThat(customerCaptured.getEmail()).isEqualTo(updateRequest.email());
        assertThat(customerCaptured.getPassword()).isEqualTo(updateRequest.password());
        assertThat(customerCaptured.getGender()).isEqualTo(updateRequest.gender());
        assertThat(customerCaptured.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canGetCustomerById() {
        //Given
        String email = FAKER.internet().safeEmailAddress();
        Customer customer = new Customer(
                FAKER.name().username(),
                FAKER.name().firstName(),
                FAKER.name().lastName(),
                email,
                FAKER.internet().password(),
                FAKER.dog().gender(),
                FAKER.random().nextInt(18, 99)
        );
        customer.setId(2L);

        when(customerDao.getByCustomerId(customer.getId())).
                thenReturn(Optional.of(customer));
        //When
        Customer actual = underTest.getCustomerById(customer.getId());
        //Then
        assertThat(actual).isEqualTo(customer);
    }


    @Test
    void canGetCustomerByIdThrowExceptionIfOptional() {
        //Given
        long id = 10L;

        when(customerDao.getByCustomerId(id))
                .thenReturn(Optional.empty());
        //Then
        assertThatThrownBy(
                () -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage("Customer with id : %s does not exist".formatted(id));
    }

    @Test
    void removeCustomerByUserNameAndEmail() {
        //Given
        String email = "lopez@codeNaren.com";
        String userName = "Keciaa12";
        when(customerDao.existsByEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                userName, "lopez", "1o",
                email, "poofi", 20, "female"
        );
        underTest.addCustomer(request);
        //When
        when(customerDao.existsByUserNameAndEmail(userName, email))
                .thenReturn(true);

        underTest.removeCustomerByUserNameAndEmail(userName, email);
        //Then
        assertThat(customerDao.existsByEmail(email)).isFalse();
    }
}