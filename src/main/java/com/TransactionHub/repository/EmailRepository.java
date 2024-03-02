package com.TransactionHub.repository;

import com.TransactionHub.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    boolean existsByEmail(String email);

    Optional<Email> findByEmail(String email);
}
