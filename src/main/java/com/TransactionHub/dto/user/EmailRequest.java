package com.TransactionHub.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(name = "Запрос на добавление/удаление адреса электронной почты")
public class EmailRequest {

    @Schema(description = "Имя пользователя", example = "Ocolus")
    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "ocolus@gmail.com")
    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 50 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Адрес электронной почты должен быть в формате user@example.com")
    private String email;

}
