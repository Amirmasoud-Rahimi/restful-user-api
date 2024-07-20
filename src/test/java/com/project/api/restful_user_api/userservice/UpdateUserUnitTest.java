package com.project.api.restful_user_api.userservice;

import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.UserRepository;
import com.project.api.restful_user_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateUserUnitTest {
    private static final Integer USER_ID = 1;
    private static final String EMAIL = "example@test.com";
    private static final String FULL_NAME = "Joe Joe";
    private static final String PASSWORD = "123";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private static User user;

    @BeforeAll
    public static void initUserEntity() {
        user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setFullName(FULL_NAME);
    }

    @Test
    public void updateUserWithoutException() {
        // Preconditions
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // Run Test
        userService.updateUser(getUserDto());

        //assertions
        verify(userRepository).save(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    public void updateUserWithException() {
        // Preconditions
        given(userRepository.findById(anyInt())).willReturn(Optional.empty());

        // Run Test & assertions
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(getUserDto()));
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(USER_ID);
        userDto.setEmail(EMAIL);
        userDto.setFullName(FULL_NAME);
        userDto.setPassword(PASSWORD);
        return userDto;
    }
}