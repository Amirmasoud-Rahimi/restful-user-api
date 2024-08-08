package com.project.api.restful_user_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Set JWT token in response dto.
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "LoginResponseDto",
        description = "The LoginResponseDto represents jwt authentication data")
public class LoginResponseDto {
    private String token;

    private long expiresIn;
}