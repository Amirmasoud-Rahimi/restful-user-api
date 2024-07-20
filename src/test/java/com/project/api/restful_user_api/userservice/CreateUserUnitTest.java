package com.project.api.restful_user_api.userservice;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.Role;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.RoleRepository;
import com.project.api.restful_user_api.repository.UserRepository;
import com.project.api.restful_user_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateUserUnitTest {
    private static final Integer USER_ID = 1;
    private static final String EMAIL = "example@test.com";
    private static final String FULL_NAME = "Joe Joe";
    private static final String PASSWORD = "123";
    private static final String DESCRIPTION = "Default user role";
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser() {
        // Preconditions
        when(userRepository.save(any(User.class))).thenReturn(getUser());
        when(roleRepository.findByName(any(RoleEnum.class))).thenReturn(Optional.of(getRole()));

        // Run Test
        User user = userService.createUser(getUserDto(), RoleEnum.USER);

        //assertions
        assertEquals(user.getId(), 1);
    }

    private User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(EMAIL);
        return user;
    }

    private Role getRole() {
        Role role = new Role();
        role.setName(RoleEnum.USER);
        role.setDescription(DESCRIPTION);
        return role;
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(EMAIL);
        userDto.setFullName(FULL_NAME);
        userDto.setPassword(PASSWORD);
        return userDto;
    }
}