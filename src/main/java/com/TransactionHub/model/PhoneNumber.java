package com.TransactionHub.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phone_numbers")
public class PhoneNumber {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number", columnDefinition = "VARCHAR(11)", unique = true, nullable = false)
    private String number;

    public PhoneNumber(String number) {
        this.number = number;
    }
}
