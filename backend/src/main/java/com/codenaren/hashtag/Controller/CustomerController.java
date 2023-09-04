package com.codenaren.hashtag.Controller;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;
import com.codenaren.hashtag.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody CustomerRegistrationRequest request) {
        log.info("Request for adding" +
                 " User in UserController called : {}", request);
        customerService.addCustomer(request);
    }


    @GetMapping("/getCustomer/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getAllCustomers")
    public List<Customer> getAllCustomers() {
        log.info("Request for getting all the Users " +
                 "method called in UserController");
        return customerService.getListOfCustomers();
    }


    @PutMapping("/updateCustomer/{id}")
    public void updateCustomer(@PathVariable("id") Long id,
                               @RequestBody CustomerUpdateRequest request) {
        log.info("Request for updating existing " +
                 " User called in UserController" +
                 " with id: {}", id);
        customerService.updateCustomer(request, id);
    }

    @DeleteMapping("/deleteUser/{userName}/{email}")
    public void removeCustomerByUserNameAndEmail(@PathVariable("userName") String username,
                                                 @PathVariable("email") String email) {
        log.info("Request for removing" +
                 " User called in UserController" +
                 " with username , email : {},{}", username, email);
        customerService.removeCustomerByUserNameAndEmail(username, email);
    }
}