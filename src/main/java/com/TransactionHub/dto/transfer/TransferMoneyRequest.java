package com.TransactionHub.dto.transfer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(name = "Запрос на перевод денег между счетами")
public class TransferMoneyRequest {

    @Schema(description = "Имя пользователя", example = "Ocolus")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    private String username;

    @Schema(description = "Номер счёта получателя", example = "1")
    private Integer recipientId;

    @Schema(description = "Сумма перевода", example = "100")
    @DecimalMin(value = "10.0", message = "Сумма перевода не может быть меньше 10.0")
    private BigDecimal amount;

}
