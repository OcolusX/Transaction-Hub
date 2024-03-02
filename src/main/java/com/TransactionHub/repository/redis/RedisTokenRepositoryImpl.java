package com.TransactionHub.repository.redis;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisTokenRepositoryImpl implements TokenRepository {

    private static final String USER_REFRESH_TOKEN_KEY = "USER_REFRESH_TOKEN";
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> findAll() {
        return hashOperations.entries(USER_REFRESH_TOKEN_KEY);
    }

    @Override
    public void add(String username, String token) {
        hashOperations.put(USER_REFRESH_TOKEN_KEY, username, token);
    }

    @Override
    public void delete(String username) {
        hashOperations.delete(USER_REFRESH_TOKEN_KEY, username);
    }

    @Override
    public String get(String username) {
        return (String) hashOperations.get(USER_REFRESH_TOKEN_KEY, username);
    }
}
