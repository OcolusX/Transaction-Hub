package com.TransactionHub.model;

import jakarta.persistence.*;

@Entity
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", unique = true, nullable = false)
    private String email;

}
