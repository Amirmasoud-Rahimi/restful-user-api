package com.project.api.restful_user_api.repository;

import com.project.api.restful_user_api.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CRUD Operations related to User entity
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Retrieve User using email(username)
     *
     * @param email as username
     * @return Optional<User>
     * @since 1.0.0
     */
    Optional<User> findByEmail(String email);
}