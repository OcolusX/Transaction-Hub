package com.TransactionHub.rest;

import com.TransactionHub.dto.jwt.JwtResponse;
import com.TransactionHub.dto.transfer.TransferMoneyRequest;
import com.TransactionHub.model.BankAccount;
import com.TransactionHub.service.BankAccountService;
import com.TransactionHub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Управление транзакциями")
public class TransactionController {

    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Operation(summary = "Перевод денег между счетами")
    @PostMapping("/transfer")
    public ResponseEntity<JwtResponse> transferMoney(@RequestBody @Valid TransferMoneyRequest request) {
        BankAccount sender = userService.getByUsername(request.getUsername()).getBankAccount();
        bankAccountService.transferMoney(sender, request.getRecipientId(), request.getAmount());
        log.info("Первод с счёта " + sender.getId() + " на счёт " + request.getRecipientId());
        return ResponseEntity.ok(new JwtResponse(HttpStatus.OK, "Перевод выполнен успешно!"));
    }

}
