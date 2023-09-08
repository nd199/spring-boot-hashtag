package com.codenaren.hashtag.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "Customer")
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique ",
                        columnNames = "email"
                )
        }
)
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @SequenceGenerator(name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq")
    private Long id;

    @Column(
            name = "user_name",
            nullable = false
    )
    private String userName;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            nullable = false
    )
    @Email
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "[A-Za-z0-9_\\-\\.]+[@][a-z]+[\\.][a-z]{2,3}"
    )
    private String email;

    @Column(
            nullable = false
    )
    //@ValidPassword
    private String password;

    @Column(
            nullable = false
    )
    private int age;

    @Column(
            nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Customer(Long id, String userName, String firstName, String lastName, String email,
                    String password, Gender gender, int age) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    public Customer(String userName,
                    String firstName, String lastName,
                    String email, String password,
                    Gender gender, int age) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return age == customer.age && Objects.equals(id, customer.id) && Objects.equals(userName, customer.userName) && Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password) && gender == customer.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName, email, password, age, gender);
    }

    @Override
    public String toString() {
        return "Customer{" +
               "id=" + id +
               ", userName='" + userName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", email='" + email + '\'' +
               ", password='" + password + '\'' +
               ", age=" + age +
               ", gender=" + gender +
               '}';
    }
}
