package com.codenaren.hashtag.Service.Impl;

import com.codenaren.hashtag.Dao.CustomerDao;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;
import com.codenaren.hashtag.Exceptions.PasswordMustBeDifferent;
import com.codenaren.hashtag.Exceptions.ResourceAlreadyExists;
import com.codenaren.hashtag.Exceptions.ResourceNotFound;
import com.codenaren.hashtag.Exceptions.ResourceSame;
import com.codenaren.hashtag.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.passay.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    private static Customer getCustomer(
            CustomerRegistrationRequest request) {
        return new Customer(
                request.userName(),
                request.firstName(),
                request.lastName(),
                request.email(),
                request.password(),
                request.gender(),
                request.age()
        );
    }

    @Override
    public void addCustomer(CustomerRegistrationRequest request) {
        log.info("addCustomer Method in CustomerServiceImpl called with request : {}", request);

        PasswordData pd = new PasswordData(request.password());
        pd.setUsername(request.userName());
        Rule rule = new UsernameRule();
        PasswordValidator passwordValidator = new PasswordValidator(rule);
        RuleResult rs = passwordValidator.validate(pd);
        if (rs.isValid()) {

            if (request.userName().equals(request.firstName()) || request.userName().equals(request.lastName())) {
                throw new ResourceSame(
                        "Customer - name and name must not be same "
                );
            } else {
                if (customerDao.existsByUserName(request.userName())) {
                    throw new ResourceAlreadyExists(
                            "Customer", "userName", request.userName()
                    );
                } else if (customerDao.existsByEmail(request.email())) {
                    throw new ResourceAlreadyExists(
                            "Customer", "email", request.email()
                    );
                } else {
                    Customer customer = getCustomer(request);
                    customerDao.registerCustomer(customer);
                }
            }
        } else {
            throw new PasswordMustBeDifferent(
                    "User-Name and Password must not be same"
            );
        }
    }


    @Override
    public void removeCustomerByUserNameAndEmail(String userName, String email) {
        log.info("removeCustomer Method in CustomerServiceImpl called with " +
                 "userName & email : {},{}", userName, email);
        if (customerDao.existsByUserNameAndEmail(userName, email)) {
            customerDao.removeCustomerByUserNameAndEmail(userName, email);
        } else {
            throw new ResourceNotFound(
                    "No Customer Found with provided Username and email : {%s},{%s}"
                            .formatted(userName, email)
            );
        }
    }

    @Override
    public List<Customer> getListOfCustomers() {
        log.info("getCustomerList Method in CustomerServiceImpl called");
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer findCustomerByUserName(String userName) {
        return customerDao.findCustomerByUserName(userName).orElseThrow(
                () -> new ResourceNotFound(
                        "Customer with userName %s does not exist".formatted(userName)
                )
        );
    }


    @Override
    public void updateCustomer(CustomerUpdateRequest updateRequest,
                               Long id) {
        log.info("update customer called in customer service impl");
        Customer customer = getCustomerById(id);

        System.out.println(customer);

        boolean isPresent = false;

        if (updateRequest.userName() != null &&
            !updateRequest.userName()
                    .equals(customer.getUserName())) {
            customer.setUserName(updateRequest.userName());
            isPresent = true;
        }
        if (updateRequest.firstName() != null &&
            !updateRequest.firstName().equals(customer.getFirstName())) {
            customer.setFirstName(updateRequest.firstName());
            isPresent = true;
        }
        if (updateRequest.lastName() != null &&
            !updateRequest.lastName().equals(customer.getLastName())) {
            customer.setLastName(updateRequest.lastName());
            isPresent = true;
        }
        if (updateRequest.password() != null &&
            !updateRequest.password().equals(customer.getPassword())) {
            customer.setPassword(updateRequest.password());
            isPresent = true;
        }
        if (updateRequest.age() != null &&
            !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            isPresent = true;
        }
        if (updateRequest.gender() != null) {
            customer.setGender(updateRequest.gender());
            isPresent = true;
        }
        if (updateRequest.email() != null &&
            !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsByEmail(updateRequest.email())) {
                throw new ResourceAlreadyExists(
                        "Customer", "email", updateRequest.email()
                );
            }
            customer.setEmail(updateRequest.email());
        }

        if (!isPresent) {
            throw new ResourceNotFound(
                    "Customer with id does not exist");
        }
        customerDao.updateCustomer(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerDao.getByCustomerId(id).orElseThrow(
                () -> new ResourceNotFound("Customer with id : %s does not exist".formatted(id))
        );
    }


}
