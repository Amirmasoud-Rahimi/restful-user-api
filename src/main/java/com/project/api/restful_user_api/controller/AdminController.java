package com.project.api.restful_user_api.controller;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController", description = "Contains all the operations that can be performed on a admin.")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody UserDto registerUserDto) {
        User createdAdmin = userService.createUser(registerUserDto, RoleEnum.ADMIN);
        return ResponseEntity.ok(createdAdmin);
    }
}