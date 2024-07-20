package com.project.api.restful_user_api.service;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.dto.UserDto;
import com.project.api.restful_user_api.entity.Role;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.RoleRepository;
import com.project.api.restful_user_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public User createUser(UserDto userDto, RoleEnum roleName) {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setIsActive(Boolean.TRUE);
        user.setRole(optionalRole.orElseThrow(EntityNotFoundException::new));

        return userRepository.save(user);
    }

    public User updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());

        User user = optionalUser.orElseThrow(EntityNotFoundException::new);
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    public void deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(EntityNotFoundException::new);
        user.setIsActive(Boolean.FALSE);
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}