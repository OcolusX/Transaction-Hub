package com.TransactionHub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "initial_amount", columnDefinition = "NUMERIC", nullable = false)
    private BigDecimal initialAmount;

    @Column(name = "current_amount", columnDefinition = "NUMERIC", nullable = false)
    private BigDecimal currentAmount;

    public BankAccount(BigDecimal initial_amount) {
        this.initialAmount = initial_amount;
        this.currentAmount = initial_amount;
    }
}
