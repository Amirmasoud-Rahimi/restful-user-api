package com.project.api.restful_user_api.configuration;

import com.project.api.restful_user_api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Perform the authentication by finding the user in the database and
 * Generate a JWT token when the authentication succeeds.
 * after adding this class we will not see the password generated in the console as before because the authentication method being overridden .
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Defines how to retrieve the user using the UserRepository that is injected
     *
     * @return UserDetailsService
     * @since 1.0.0
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
    }

    /**
     * Creates an instance of the BCryptPasswordEncoder used to encode the plain user password
     * BCrypt is a one-way strong hashing function
     *
     * @return BCryptPasswordEncoder
     * @since 1.0.0
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Manage authentication configuration
     *
     * @param config
     * @return AuthenticationManager
     * @throws Exception
     * @since 1.0.0
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Sets the new strategy to perform the authentication
     *
     * @return AuthenticationProvider
     * @since 1.0.0
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}