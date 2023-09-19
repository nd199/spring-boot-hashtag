package com.codenaren.hashtag.Service.Impl;

import com.codenaren.hashtag.Dao.CustomerDao;
import com.codenaren.hashtag.Dto.CustomerDTO;
import com.codenaren.hashtag.Dto.Mapper.CustomerDTOMapper;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    private final CustomerDTOMapper customerDTOMapper;

    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(@Qualifier("jpa") CustomerDao customerDao,
                               CustomerDTOMapper customerDTOMapper,
                               PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private Customer getCustomer(
            CustomerRegistrationRequest request) {
        return new Customer(
                request.userName(),
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password()),
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
                            "Username already taken"
                    );
                } else if (customerDao.existsByEmail(request.email())) {
                    throw new ResourceAlreadyExists(
                            "Email already taken"
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
    public List<CustomerDTO> getListOfCustomers() {
        log.info("getCustomerList Method in CustomerServiceImpl called");
        return customerDao.getAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findCustomerByUserName(String userName) {
        return customerDao.findCustomerByUserName(userName)
                .map(customerDTOMapper)
                .orElseThrow(
                        () -> new ResourceNotFound(
                                "Customer with userName %s does not exist".formatted(userName)
                        )
                );
    }


    @Override
    public void updateCustomer(CustomerUpdateRequest updateRequest,
                               Long id) {
        log.info("update customer called in customer service impl");
        Customer customer = customerDao.getByCustomerId(id)
                .orElseThrow(()
                        -> new ResourceNotFound(
                        "Customer with id [%s] not found".formatted(id))
                );
        boolean isPresent = false;

        if (updateRequest.userName() != null &&
            !updateRequest.userName()
                    .equals(customer.getUsername())) {
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
                        "Email already taken"
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
    public CustomerDTO getCustomerById(Long id) {
        return customerDao.getByCustomerId(id)
                .map(customerDTOMapper)
                .orElseThrow(()
                        -> new ResourceNotFound(
                        "Customer with id [%s] not found".formatted(id))
                );
    }

    @Override
    public void removeCustomerById(Long customerId) {
        if (!customerDao.existsCustomerById(customerId)) {
            throw new ResourceNotFound(
                    "Customer with id [%s] not found".formatted(customerId)
            );
        }
        customerDao.removeCustomerById(customerId);
    }


}
