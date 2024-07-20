package com.project.api.restful_user_api.userservice;

import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.UserRepository;
import com.project.api.restful_user_api.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteUserUnitTest {
    private static final Integer USER_ID = 1;
    private static final String EMAIL = "example@test.com";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deleteWithoutException() {
        // Preconditions
        User user = getUserEntity();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Run Test
        userService.deleteUser(USER_ID);

        //assertions
        verify(userRepository).save(user);
        assertEquals(user.getIsActive(), Boolean.FALSE);
    }

    @Test
    public void deleteWithException() {
        // Preconditions
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Run Test & assertions
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(USER_ID));
        verify(userRepository, times(0)).save(getUserEntity());
    }

    private User getUserEntity() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        user.setIsActive(Boolean.TRUE);
        return user;
    }
}