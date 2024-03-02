package com.TransactionHub.rest;

import com.TransactionHub.dto.search_user.SearchUserRequest;
import com.TransactionHub.model.Email;
import com.TransactionHub.model.PhoneNumber;
import com.TransactionHub.model.User;
import com.TransactionHub.repository.UserRepository;
import com.TransactionHub.service.UserService;
import com.TransactionHub.specification.UserSpecification;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Поиск пользователей")
public class SearchUserController {

    private final UserRepository userRepository;
    @Autowired
    private UserSpecification userSpecification;


    @GetMapping("/search")
    public Map<String, Map<String, String>> searchUser(
            @RequestBody @Valid SearchUserRequest request,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        Specification<User> specification = userSpecification.build(request);
        Page<User> all = userRepository.findAll(specification, PageRequest.of(page - 1, limit));

        Map<String, Map<String, String>> users = new TreeMap<>();
        for (User user : all) {
            Map<String, String> info = new HashMap<>();
            info.put("emails", user.getEmails().stream()
                    .map(Email::getEmail)
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .get());
            info.put("phoneNumbers", user.getPhoneNumbers().stream()
                    .map(PhoneNumber::getNumber)
                    .reduce(((s1, s2) -> s1 + ", " + s2))
                    .get());
            info.put("birthDay", user.getBirthday().toString());
            users.put(user.getFullName(), info);
        }
        return users;
    }

}
