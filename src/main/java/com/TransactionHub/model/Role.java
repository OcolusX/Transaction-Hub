package com.TransactionHub.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", columnDefinition = "VARCHAR(10)", nullable = false)
    private String name;

    @ManyToMany()
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
