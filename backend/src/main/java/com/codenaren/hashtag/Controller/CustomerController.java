package com.codenaren.hashtag.Controller;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;
import com.codenaren.hashtag.Service.CustomerService;
import com.codenaren.hashtag.Utils.Jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final JWTUtil jwtUtil;

    @PostMapping("/customers")
    public ResponseEntity<?> addCustomer(
            @RequestBody CustomerRegistrationRequest request) {
        log.info("Request for adding" +
                 " User in UserController called : {}", request);
        customerService.addCustomer(request);
        String token = jwtUtil.issueToken(request.userName(),
                "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }


    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        log.info("Request for getting all the Users " +
                 "method called in UserController");
        return customerService.getListOfCustomers();
    }


    @PutMapping("/customers/{id}")
    public void updateCustomer(@PathVariable("id") Long id,
                               @RequestBody CustomerUpdateRequest request) {
        log.info("Request for updating existing " +
                 " User called in UserController" +
                 " with id: {}", id);
        customerService.updateCustomer(request, id);
    }

    @DeleteMapping("/customers/{id}")
    public void removeCustomerByUserNameAndEmail(@PathVariable("id") Long id) {
        log.info("Request for removing" +
                 " User called in UserController" +
                 " with username , email : {}", id);
        customerService.removeCustomerById(id);
    }
}