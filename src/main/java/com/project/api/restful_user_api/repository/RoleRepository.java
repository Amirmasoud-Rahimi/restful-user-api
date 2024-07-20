package com.project.api.restful_user_api.repository;

import com.project.api.restful_user_api.constant.RoleEnum;
import com.project.api.restful_user_api.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum roleName);
}