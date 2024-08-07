package com.project.api.restful_user_api.constant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * User Roles Enumeration to have different privilege.
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */

@Schema(title = "RoleEnum",
        description = "The RoleEnum enumeration represents information about the types of users roles to have different privileges")
public enum RoleEnum {
    USER,
    ADMIN,
    SUPER_ADMIN
}