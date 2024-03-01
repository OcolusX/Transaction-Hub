package com.TransactionHub.setvice;

import com.TransactionHub.model.Role;
import com.TransactionHub.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfoNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role getUserRole() {
        return repository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
    }
}
