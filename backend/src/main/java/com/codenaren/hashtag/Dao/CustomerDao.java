package com.codenaren.hashtag.Dao;


import com.codenaren.hashtag.Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsCustomerById(Long id);

    void registerCustomer(Customer user);

    void removeCustomerById(Long id);

    void updateCustomer(Customer user);

    List<Customer> getAllCustomers();

    Optional<Customer> findCustomerByUserName(String userName);

    Optional<Customer> getByCustomerId(Long id);
}
