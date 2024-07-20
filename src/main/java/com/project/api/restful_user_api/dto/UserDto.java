package com.project.api.restful_user_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer id;

    private String fullName;

    private String email;

    private String password;
}