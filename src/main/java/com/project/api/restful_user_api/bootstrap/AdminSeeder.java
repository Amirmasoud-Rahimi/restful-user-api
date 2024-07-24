package com.project.api.restful_user_api.bootstrap;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.entity.Role;
import com.project.api.restful_user_api.entity.User;
import com.project.api.restful_user_api.repository.RoleRepository;
import com.project.api.restful_user_api.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    private static final Integer SUPER_ADMIN_ID = 777;
    private static final String SUPER_ADMIN_EMAIL = "super.admin@test.com";
    private static final String SUPER_ADMIN_FULL_NAME = "SuperAdmin Admin";
    private static final String SUPER_ADMIN_PASSWORD = "super@admin";
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * set priority to invoke event Listener -  The lower the value, the sooner your listener will be invoked
     * @return integer number
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(SUPER_ADMIN_EMAIL);

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user.setFullName(SUPER_ADMIN_FULL_NAME);
        user.setEmail(SUPER_ADMIN_EMAIL);
        user.setPassword(passwordEncoder.encode(SUPER_ADMIN_PASSWORD));
        user.setIsActive(Boolean.TRUE);
        user.setRole(optionalRole.get());
        userRepository.save(user);
    }
}