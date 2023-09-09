package com.codenaren.hashtag.Dao.Impl;

import com.codenaren.hashtag.Dao.CustomerDao;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("jpa")
@Slf4j
@RequiredArgsConstructor
public class CustomerDaoImpl implements CustomerDao {

    private final CustomerRepository customerRepository;

    @Override
    public boolean existsByEmail(String email) {
        log.info("CustomerDaoImpl method on existByEmail called : {}", email);
        log.info("customerRepository method on existByEmail called : {}", email);
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUserName(String userName) {
        log.info("CustomerDaoImpl method on existByCustomerName called : {}", userName);
        log.info("customerRepository method on existByCustomerName called : {}", userName);
        return customerRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsCustomerById(Long id) {
        log.info("CustomerDaoImpl method on existByCustomerName called : {}", id);
        log.info("customerRepository method on existByCustomerName called : {}", id);
        return customerRepository.existsCustomerById(id);
    }

    @Override
    public void registerCustomer(Customer customer) {
        log.info("CustomerDaoImpl method on registerCustomer called : {}", customer);
        log.info("CustomerDaoImpl method on saveCustomer called : {}", customer);
        customerRepository.save(customer);
    }

    @Override
    public void removeCustomerById(Long id) {
        log.info("CustomerDaoImpl method on removeCustomer called : {}", id);
        log.info("customerRepository method on deleteByCustomerNameAndEmail" +
                 " called : {}", id);
        customerRepository.deleteCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        log.info("CustomerDaoImpl method on updateCustomer called : {}", customer);
        customerRepository.save(customer);
    }


    @Override
    public List<Customer> getAllCustomers() {
        log.info("CustomerDaoImpl method on getAllAdmins" +
                 " called ");
        log.info("customerRepository method on findAll" +
                 " called ");
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerByUserName(String userName) {
        return customerRepository.getCustomerByUserName(userName);
    }

    @Override
    public Optional<Customer> getByCustomerId(Long id) {
        return customerRepository.findById(id);
    }
}
