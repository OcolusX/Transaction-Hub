package com.TransactionHub.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Запрос на получение refresh токена")
public class RefreshJwtRequest {

    @Schema(name = "Refresh токен", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ...")
    private String refreshToken;

}
