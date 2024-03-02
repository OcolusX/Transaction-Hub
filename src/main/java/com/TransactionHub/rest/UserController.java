package com.TransactionHub.rest;

import com.TransactionHub.dto.user.EmailRequest;
import com.TransactionHub.dto.user.PhoneNumberRequest;
import com.TransactionHub.dto.jwt.JwtResponse;
import com.TransactionHub.model.User;
import com.TransactionHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Управление пользователем")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Добавление нового номера телефона")
    @PostMapping("/add/phoneNumber")
    public ResponseEntity<JwtResponse> addPhoneNumber(@RequestBody @Valid PhoneNumberRequest request) {
        User user = userService.getByUsername(request.getUsername());
        userService.addPhoneNumber(user, request.getPhoneNumber());
        return ResponseEntity.ok(new JwtResponse(HttpStatus.OK, "Номер телефона успешно добавлен"));
    }

    @Operation(summary = "Удаление номера телефона")
    @PostMapping("/remove/phoneNumber")
    public ResponseEntity<JwtResponse> removePhoneNumber(@RequestBody @Valid PhoneNumberRequest request) {
        User user = userService.getByUsername(request.getUsername());
        userService.removePhoneNumber(user, request.getPhoneNumber());
        return ResponseEntity.ok(new JwtResponse(HttpStatus.OK, "Номер телефона успешно удалён"));
    }

    @Operation(summary = "Добавление адреса электронной почты")
    @PostMapping("/add/email")
    public ResponseEntity<JwtResponse> addEmail(@RequestBody @Valid EmailRequest request) {
        User user = userService.getByUsername(request.getUsername());
        userService.addEmail(user, request.getEmail());
        return ResponseEntity.ok(new JwtResponse(HttpStatus.OK, "Адрес электронной почты успешно добавлен"));
    }

    @Operation(summary = "Удаление адреса электронной почты")
    @PostMapping("/remove/email")
    public ResponseEntity<JwtResponse> removeEmail(@RequestBody @Valid EmailRequest request) {
        User user = userService.getByUsername(request.getUsername());
        userService.removeEmail(user, request.getEmail());
        return ResponseEntity.ok(new JwtResponse(HttpStatus.OK, "Адрес электронной почты успешно удалён"));
    }

}
