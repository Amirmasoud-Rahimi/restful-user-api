package com.project.api.restful_user_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Size(min = 3, max = 128, message = "The length of full name must be between 3 and 128 characters.")
    @NotEmpty(message = "The full name is required.")
    private String fullName;

    @Email(message = "The email address is invalid.", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @NotEmpty(message = "The email is required.")
    private String email;

    @NotEmpty(message = "The password is required.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{3,}$", flags = {Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE},
            message = "The Password is invalid.Please use numbers , letters and special characters.")
    private String password;
}