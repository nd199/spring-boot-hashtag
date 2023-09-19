package com.codenaren.hashtag.Service;

import com.codenaren.hashtag.Dto.CustomerDTO;
import com.codenaren.hashtag.EntityRecord.CustomerRegistrationRequest;
import com.codenaren.hashtag.EntityRecord.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerRegistrationRequest request);

    List<CustomerDTO> getListOfCustomers();

    CustomerDTO findCustomerByUserName(String userName);

    void updateCustomer(CustomerUpdateRequest request, Long id);

    CustomerDTO getCustomerById(Long id);

    void removeCustomerById(Long id);
}
