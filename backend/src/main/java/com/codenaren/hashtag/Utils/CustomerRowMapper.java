package com.codenaren.hashtag.Utils;

import com.codenaren.hashtag.Entity.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Customer(
                rs.getLong("id"),
                rs.getString("user_name"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("gender"),
                rs.getInt("age")
        );
    }
}
