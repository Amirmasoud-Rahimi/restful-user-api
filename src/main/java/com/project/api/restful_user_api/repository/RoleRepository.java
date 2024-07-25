package com.project.api.restful_user_api.repository;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * CRUD Operations related to Role entity
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {

    /**
     * Retrieve Role using roleName
     *
     * @param roleName from RoleEnum list
     * @return Optional<Role>
     * @since 1.0.0
     */
    Optional<Role> findByName(RoleEnum roleName);
}