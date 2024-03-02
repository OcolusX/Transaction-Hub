package com.TransactionHub.repository.redis;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TokenRepository {
    Map<Object, Object> findAll();
    void add(String userName, String token);
    void delete(String username);
    String get(String username);
}
