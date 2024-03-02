package com.TransactionHub.service.jwt;

import com.TransactionHub.dto.jwt.JwtAuthenticationResponse;
import com.TransactionHub.dto.sign.SignInRequest;
import com.TransactionHub.dto.sign.SignUpRequest;
import com.TransactionHub.model.*;
import com.TransactionHub.repository.redis.TokenRepository;
import com.TransactionHub.service.RoleService;
import com.TransactionHub.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final TokenRepository refreshTokenRepository;

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
        refreshTokenRepository.add(user.getUsername(), refreshToken);

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
        refreshTokenRepository.add(user.getUsername(), refreshToken);

        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }

    public JwtAuthenticationResponse refresh(String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            Claims refreshClaims = jwtService.getRefreshClaims(refreshToken);
            String username = refreshClaims.getSubject();
            String saveRefreshToken = refreshTokenRepository.get(username);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                User user = userService.getByUsername(username);
                String accessToken = jwtService.generateAccessToken(user);
                String newRefreshToken = jwtService.generateRefreshToken(user);
                refreshTokenRepository.add(username, newRefreshToken);
                return new JwtAuthenticationResponse(accessToken, refreshToken);
            }
        }
        return new JwtAuthenticationResponse(null, null);
    }


}
