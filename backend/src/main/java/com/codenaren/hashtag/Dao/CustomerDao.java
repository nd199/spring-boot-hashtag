package com.codenaren.hashtag.Dao;


import com.codenaren.hashtag.Entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    void registerCustomer(Customer user);

    void removeCustomerByUserNameAndEmail(String userName, String email);

    boolean existsByUserNameAndEmail(String userName, String email);

    void updateCustomer(Customer user);

    List<Customer> getAllCustomers();

    Optional<Customer> findCustomerByUserName(String userName);

    Optional<Customer> getByCustomerId(Long id);
}
