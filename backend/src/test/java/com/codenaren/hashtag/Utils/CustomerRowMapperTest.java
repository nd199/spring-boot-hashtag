package com.codenaren.hashtag.Utils;

import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Entity.Gender;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {


    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("userName")).thenReturn("wendy.rose11");
        when(resultSet.getString("firstName")).thenReturn("Wendy");
        when(resultSet.getString("lastName")).thenReturn("rose");
        when(resultSet.getString("email")).thenReturn("wendyrose.23331@gmail.com");
        when(resultSet.getString("password")).thenReturn("22@dHaasncasw");
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("gender")).thenReturn("FEMALE");


        //When
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        //Then
        Customer toBeReceived = new Customer(
                1L, "wendy.rose11", "Wendy", "rose", "wendyrose.23331@gmail.com", "22@dHaasncasw", Gender.FEMALE, 19
        );
    }
}