package com.TransactionHub.service;

import com.TransactionHub.model.Role;
import com.TransactionHub.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role getUserRole() {
        return repository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
    }
}
