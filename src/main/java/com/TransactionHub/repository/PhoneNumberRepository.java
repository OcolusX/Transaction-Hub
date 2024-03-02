package com.TransactionHub.repository;

import com.TransactionHub.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Integer> {

    boolean existsByNumber(String number);

    Optional<PhoneNumber> findByNumber(String number);

    void deleteByNumber(String number);

}
