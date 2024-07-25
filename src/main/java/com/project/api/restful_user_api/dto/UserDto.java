package com.project.api.restful_user_api.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * To create and update User entity.
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Getter
@Setter
public class UserDto {
    private Integer id;

    private String fullName;

    private String email;

    private String password;
}