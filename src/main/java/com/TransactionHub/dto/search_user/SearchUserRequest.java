package com.TransactionHub.dto.search_user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;

@Data
@Schema(name = "Запрос на поиск пользователей")
public class SearchUserRequest {

    private Date birthday;
    private String phoneNumber;
    private String fullName;
    private String email;

}
