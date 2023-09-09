package com.codenaren.hashtag.Service;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerRegistrationRequest request);

    List<Customer> getListOfCustomers();

    Customer findCustomerByUserName(String userName);

    void updateCustomer(CustomerUpdateRequest request, Long id);

    Customer getCustomerById(Long id);

    void removeCustomerById(Long id);
}
