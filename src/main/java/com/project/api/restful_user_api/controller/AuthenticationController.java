package com.project.api.restful_user_api.controller;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.LoginResponseDto;
import com.project.api.restful_user_api.dto.LoginUserDto;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.service.JwtService;
import com.project.api.restful_user_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthenticationController",
        description = "Contains all the authentication operations")
@ApiResponses(
        {
                @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
                @ApiResponse(responseCode = "404", description = "The User with given Id was not found"),
                @ApiResponse(responseCode = "500", description = "Unknown internal server error")
        })
public class AuthenticationController {
    private final JwtService jwtService;

    private final UserService userService;

    public AuthenticationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Operation(summary = "signUp",
            description = "This method creates a new user")
    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@Parameter(description = "New User information to persist in database")
                                       @Valid @RequestBody UserDto registerUserDto) {
        User registeredUser = userService.createUser(registerUserDto, RoleEnum.USER);
        Link link = linkTo(methodOn(AuthenticationController.class).signIn(new LoginUserDto())).withRel("signIn");
        registeredUser.add(link);

        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "signIn",
            description = "This method authenticates user")
    @PostMapping("/signIn")
    public ResponseEntity<LoginResponseDto> signIn(@Parameter(description = "User login information")
                                                   @Valid @RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = userService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = LoginResponseDto
                .builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "update",
            description = "This method updates user")
    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@Parameter(description = "User information to update in database")
                                           @Valid @RequestBody UserDto userDto) {
        User updateUser = userService.updateUser(userDto);
        Link link = linkTo(methodOn(AuthenticationController.class).signUp(userDto)).withRel("signUp");
        updateUser.add(link);

        return ResponseEntity.ok(updateUser);
    }
}