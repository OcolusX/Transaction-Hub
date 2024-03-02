package com.TransactionHub.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Запрос на добавление/удаление номера телефона")
public class PhoneNumberRequest {

    @Schema(description = "Имя пользователя", example = "Ocolus")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Номер телефона", example = "+71234567890")
    @Pattern(regexp = "\\+\\d{11}", message = "Номер телефона должен быть в формате +71234567890")
    private String phoneNumber;

}
