package com.TransactionHub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Ответ с токеном доступа")
public class JwtAuthenticationResponse {

    @Schema(description = "Токен доступа", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ...")
    private String token;
}
