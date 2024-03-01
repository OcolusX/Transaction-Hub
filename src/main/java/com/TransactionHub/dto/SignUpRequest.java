package com.TransactionHub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя", example = "Ocolus")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Пароль", example = "Aa12345")
    @Size(min = 5, max = 50, message = "Пароль должен содержать от 5 до 50 символов")
    private String password;

    @Schema(description = "Начальная сумма", example = "100")
    @DecimalMin(value = "100.0", message = "Начальная сумма не может быть меньше 100")
    private BigDecimal amount;

    @Schema(description = "ФИО", example = "Мельчаков Дмитрий Сергеевич")
    @NotBlank(message = "ФИО не может быть пустым")
    private String fullName;

    @Schema(description = "Дата рождения", example = "2002-07-17")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthday;

    @Schema(description = "Номер телефона", example = "+71234567890")
    @Pattern(regexp = "\\+\\d{11}", message = "Номер телефона должен быть в формате +71234567890")
    private String phoneNumber;

    @Schema(description = "Адрес электронной почты", example = "ocolus@gmail.com")
    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 50 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@example.com")
    private String email;


}
