package com.project.api.restful_user_api.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "UserController",
        description = "Contains all the operations that can be performed on a user")
@ApiResponses(
        {
                @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
                @ApiResponse(responseCode = "401", description = "You are not authorized to access this resource"),
                @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource"),
                @ApiResponse(responseCode = "404", description = "The User with given Id was not found"),
                @ApiResponse(responseCode = "500", description = "Unknown internal server error")
        })
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    @Operation(summary = "authenticatedUser",
            description = "This method returns authenticated user information")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/users")
    @Operation(summary = "allUsers",
            description = "This method returns all users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "findById",
            description = "This method fetches user by user id")
    public ResponseEntity<User> findById(@Parameter(description = "User id to find in database")
                                         @PathVariable Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/greeting/{id}")
    @Operation(summary = "welcomeUser",
            description = "This method show welcome message to user")
    public ResponseEntity<String> welcomeUser(@Parameter(description = "User id to find in database") @PathVariable Integer id) {
        User user = userService.findById(id);
        String message = "Hello Dear User : " + user.getFullName();
        return ResponseEntity.ok(message);
    }
}