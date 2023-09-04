package com.codenaren.hashtag.Repository;


import com.codenaren.hashtag.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByUserNameAndEmail(String userName, String email);

    Optional<Customer> getCustomerById(Long id);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<Customer> getCustomerByUserName(String userName);

    void deleteByUserNameAndEmail(String userName, String email);
}
