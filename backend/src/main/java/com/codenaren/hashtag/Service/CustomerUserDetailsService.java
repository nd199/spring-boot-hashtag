package com.codenaren.hashtag.Service;

import com.codenaren.hashtag.Dao.CustomerDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerDao customerDao;

    public CustomerUserDetailsService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        return customerDao.findCustomerByUserName(userName)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Username %s not found".
                                        formatted(userName)
                        ));
    }
}
