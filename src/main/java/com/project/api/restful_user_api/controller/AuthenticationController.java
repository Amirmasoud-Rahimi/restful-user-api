package com.project.api.restful_user_api.controller;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.LoginResponseDto;
import com.project.api.restful_user_api.dto.LoginUserDto;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.service.JwtService;
import com.project.api.restful_user_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthenticationController", description = "Authentication operations")
public class AuthenticationController {
    private final JwtService jwtService;

    private final UserService userService;

    public AuthenticationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "signUp", description = "signUp user")
    @PostMapping("/signUp")
    public ResponseEntity<User> register(@RequestBody UserDto registerUserDto) {
        User registeredUser = userService.createUser(registerUserDto, RoleEnum.USER);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "signIn", description = "signIn user")
    @PostMapping("/signIn")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = userService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = LoginResponseDto
                .builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "update", description = "update user")
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) {
        User updateUser = userService.updateUser(userDto);
        return ResponseEntity.ok(updateUser);
    }
}