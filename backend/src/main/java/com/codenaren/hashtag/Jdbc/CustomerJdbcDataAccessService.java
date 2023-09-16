package com.codenaren.hashtag.Jdbc;

import com.codenaren.hashtag.Dao.CustomerDao;
import com.codenaren.hashtag.Entity.Customer;
import com.codenaren.hashtag.Exceptions.ResourceNotFound;
import com.codenaren.hashtag.Utils.CustomerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository("jdbc")
public class CustomerJdbcDataAccessService
        implements CustomerDao {

    private final CustomerRowMapper customerRowMapper;
    private final JdbcTemplate jdbcTemplate;


    public CustomerJdbcDataAccessService(CustomerRowMapper customerRowMapper, JdbcTemplate jdbcTemplate) {
        this.customerRowMapper = customerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsByEmail(String email) {

        boolean exist = true;

        var sql = """
                SELECT  id, user_name, first_name, last_name, email, password, gender, age
                from customer where email = ?
                """;

        Customer cus = jdbcTemplate.query(sql, customerRowMapper, email)
                .stream().findFirst().orElseThrow(
                        () -> new ResourceNotFound("User does not exist with user name : " + email)
                );

        if (!Objects.nonNull(cus)) {
            exist = false;
        }
        return exist;
    }

    @Override
    public boolean existsByUserName(String userName) {
        boolean exist = true;

        var sql = """
                SELECT  id, user_name, first_name, last_name, email, password, gender, age
                from customer where email = ?
                """;

        Customer cus = jdbcTemplate.query(sql, customerRowMapper, userName)
                .stream().findFirst().orElseThrow(
                        () -> new ResourceNotFound("User does not exist with user name : " + userName)
                );

        if (!Objects.nonNull(cus)) {
            exist = false;
        }
        return exist;
    }

    @Override
    public boolean existsCustomerById(Long id) {
        boolean exist = true;

        var sql = """
                SELECT  id, user_name, first_name, last_name, email, password, gender, age
                from customer where id = ?
                """;

        Customer cus = jdbcTemplate.query(sql, customerRowMapper, id)
                .stream().findFirst().orElseThrow(
                        () -> new ResourceNotFound("User does not exist with user name : " + id)
                );

        if (!Objects.nonNull(cus)) {
            exist = false;
        }
        return exist;
    }

    @Override
    public void registerCustomer(Customer customer) {
        var sql = """
                               
                 INSERT  INTO  customer(
                user_name, first_name, last_name, email, password, age, gender
                ) VALUES (?,?,?,?,?,?,?)
                """;

        int update = jdbcTemplate.update(sql
                , customer.getUsername(),
                customer.getFirstName(), customer.getLastName(),
                customer.getEmail(), customer.getPassword(),
                customer.getAge(), customer.getGender().name()
        );
        System.out.println("Jdbc template.update" + update);
    }

    @Override
    public void removeCustomerById(Long id) {
        var sql = """
                DELETE FROM customer WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateCustomer(Customer update) {
        String sql;
        if (update.getUsername() != null) {
            sql = "UPDATE customer SET  user_name = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getUsername(), update.getId());
        }
        if (update.getFirstName() != null) {
            sql = "UPDATE customer SET  first_name = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getFirstName(), update.getId());
        }
        if (update.getLastName() != null) {
            sql = "UPDATE customer SET  last_name = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getLastName(), update.getId());
        }
        if (update.getEmail() != null) {
            sql = "UPDATE customer SET  email = ? WHERE id = ?";
            jdbcTemplate.update
                    (sql, update.getEmail(), update.getId());
        }
        if (update.getPassword() != null) {
            sql = "UPDATE customer SET  password = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getPassword(), update.getId());
        }
        if (update.getAge() >= 15) {
            sql = "UPDATE customer SET  age = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getAge(), update.getId());
        }
        if (update.getGender() != null) {
            sql = "UPDATE customer SET  gender = ? WHERE id = ?";
            jdbcTemplate.update(sql, update.getGender(), update.getId());
        }
    }

    @Override
    public Optional<Customer> getByCustomerId(Long id) {

        var sql =
                """
                        SELECT  id, user_name, first_name, last_name, email, password, age, gender
                        from customer WHERE id = ?
                        """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream().findFirst();
    }


    @Override
    public List<Customer> getAllCustomers() {
        var sql = """
                SELECT  id, user_name, first_name, last_name, email, password, age, gender
                from customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> findCustomerByUserName(String userName) {
        var sql = """
                SELECT  id, user_name, first_name, last_name, email, password, age, gender
                from customer where user_name = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, userName)
                .stream().findFirst();
    }

}
