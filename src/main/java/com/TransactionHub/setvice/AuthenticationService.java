package com.TransactionHub.setvice;

import com.TransactionHub.dto.JwtAuthenticationResponse;
import com.TransactionHub.dto.SignInRequest;
import com.TransactionHub.dto.SignUpRequest;
import com.TransactionHub.model.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final String USER_REFRESH_TOKEN_CACHE = "USER_REFRESH_TOKEN";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, String> hashOperations;

//    @PostConstruct
//    private void initializeHashOperations() {
//        hashOperations = redisTemplate.opsForHash();
//    }

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        phoneNumbers.add(new PhoneNumber(request.getPhoneNumber()));

        Set<Email> emails = new HashSet<>();
        emails.add(new Email(request.getEmail()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .bankAccount(new BankAccount(request.getAmount()))
                .fullName(request.getFullName())
                .birthday(request.getBirthday())
                .phoneNumbers(phoneNumbers)
                .emails(emails)
                .roles(roles)
                .build();

        userService.create(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
//        hashOperations.put(USER_REFRESH_TOKEN_CACHE, user.getUsername(), refreshToken);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
//        hashOperations.put(USER_REFRESH_TOKEN_CACHE, user.getUsername(), refreshToken);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }


}
