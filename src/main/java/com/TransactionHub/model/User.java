package com.TransactionHub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "full_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String fullName;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(11)", nullable = false)
    private String phoneNumber;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @Column(name = "birthday", columnDefinition = "DATE", nullable = false)
    private LocalDate birthday;

    @Column(name = "login", columnDefinition = "VARCHAR(50)", nullable = false)
    private String login;

    @Column(name = "password", columnDefinition = "VARCHAR(50)", nullable = false)
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount bankAccount;
}
