package com.TransactionHub.specification;

import com.TransactionHub.dto.search_user.SearchUserRequest;
import com.TransactionHub.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserSpecification {

    public Specification<User> build(SearchUserRequest request) {
        return withBirthday(request.getBirthday())
                .and(withPhoneNumber(request.getPhoneNumber()))
                .and(withFullName(request.getFullName()))
                .and(withEmail(request.getEmail()));
    }

    private Specification<User> withBirthday(Date birthday) {
        return (root, query, criteriaBuilder) -> birthday == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.greaterThan(root.get("birthday"), birthday);
    }

    private Specification<User> withPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> phoneNumber == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber);
    }

    private Specification<User> withFullName(String fullName) {
        return (root, query, criteriaBuilder) -> fullName == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.like(root.get("fullName"), fullName + "%");
    }

    private Specification<User> withEmail(String email) {
        return (root, query, criteriaBuilder) -> email == null ?
                criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("email"), email);
    }

}
