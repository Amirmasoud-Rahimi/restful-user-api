package com.project.api.restful_user_api.userservice;

import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.UserRepository;
import com.project.api.restful_user_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class FindAllUsersUnitTest {
    private static final Integer USER_ID = 1;
    private static final String EMAIL = "example@test.com";
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAllUsers() {
        // Preconditions
        given(userRepository.findAll()).willReturn(getUserList());

        // Run Test
        List<User> result = userService.getAllUsers();

        //assertions
        assertEquals(1, result.size(), "findAllUsers, size failed.");
    }

    private Page<User> getUserList() {
        List<User> list = new ArrayList<>();
        User entity = new User();
        entity.setId(USER_ID);
        entity.setEmail(EMAIL);
        list.add(entity);
        return new PageImpl<>(list);
    }
}