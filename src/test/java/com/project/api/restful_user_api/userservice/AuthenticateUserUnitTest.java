package com.project.api.restful_user_api.userservice;

import com.project.api.restful_user_api.dto.LoginUserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.UserRepository;
import com.project.api.restful_user_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticateUserUnitTest {
    private static final Integer USER_ID = 1;
    private static final String EMAIL = "example@test.com";
    private static final String PASSWORD = "123";

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @Test
    public void authenticateUserWithoutException() {
        //preconditions
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(new UsernamePasswordAuthenticationToken(getLoginUserDto().getEmail(), getLoginUserDto().getPassword()));

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(getUserEntity()));

        //Run test
        User result = userService.authenticate(getLoginUserDto());

        //assertions
        assertEquals(getUserEntity().getEmail(), result.getEmail());
    }

    @Test
    public void authenticateUserWithException() {
        //preconditions
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenThrow(EntityNotFoundException.class);

        //Run test and assertions
        assertThrows(EntityNotFoundException.class, () -> userService.authenticate(getLoginUserDto()));
    }

    private LoginUserDto getLoginUserDto() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail(EMAIL);
        loginUserDto.setPassword(PASSWORD);
        return loginUserDto;
    }

    private User getUserEntity() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        return user;
    }
}