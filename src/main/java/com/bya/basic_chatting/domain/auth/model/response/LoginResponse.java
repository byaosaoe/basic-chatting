package com.bya.basic_chatting.domain.auth.model.response;

import com.bya.basic_chatting.common.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Login response")
public record LoginResponse(

    @Schema(description = "error code")
    ErrorCode description,

    @Schema(description = "jwt token")
    String token
) {}
