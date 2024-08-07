package com.project.api.restful_user_api.controller;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "AdminController",
        description = "Contains all the operations that can be performed on an Admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "create Administrator",
            description = "This method creates a new Administrator")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
                    @ApiResponse(responseCode = "401", description = "You are not authorized to access this resource"),
                    @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource"),
                    @ApiResponse(responseCode = "404", description = "The User with given Id was not found"),
                    @ApiResponse(responseCode = "500", description = "Unknown internal server error")
            })
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@Parameter(description = "New admin information to persist in database")
                                                    @RequestBody UserDto registerUserDto) {
        User createdAdmin = userService.createUser(registerUserDto, RoleEnum.ADMIN);
        return ResponseEntity.ok(createdAdmin);
    }
}