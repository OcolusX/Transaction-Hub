package com.TransactionHub.rest;

import com.TransactionHub.dto.jwt.JwtAuthenticationResponse;
import com.TransactionHub.dto.sign.SignInRequest;
import com.TransactionHub.dto.sign.SignUpRequest;
import com.TransactionHub.dto.jwt.RefreshJwtRequest;
import com.TransactionHub.service.jwt.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @Operation(summary = "Получить новый refresh токен")
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> getNewRefreshToken(@RequestBody @Valid RefreshJwtRequest request) {
        return ResponseEntity.ok(authenticationService.refresh(request.getRefreshToken()));
    }

}
