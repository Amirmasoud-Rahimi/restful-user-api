package com.project.api.restful_user_api.bootstrap;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.entity.Role;
import com.project.api.restful_user_api.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * Create and save roles (USER,ADMIN,SUPER_ADMIN) and descriptions in database on application startup.
 * You can invoke an evenListener class sooner than others with implement Ordered interface and override getOrder().
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Set priority to invoke event Listener on application startup.
     * The lower the value , the sooner your listener will be invoked.
     *
     * @return integer number (LOWEST_PRECEDENCE means your listener will be invoked sooner than others and has last priority)
     * @since 1.0.0
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    /**
     * Run on application startup
     *
     * @param contextRefreshedEvent
     * @since 1.0.0
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    /**
     * Create user with super-admin role and default properties
     *
     * @since 1.0.0
     */
    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
                RoleEnum.USER, "Default user role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.SUPER_ADMIN, "Super Administrator role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToCreate);
            });
        });
    }
}