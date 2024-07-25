package com.project.api.restful_user_api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Login User data.
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Getter
@Setter
public class LoginUserDto {
    private String email;
    private String password;
}