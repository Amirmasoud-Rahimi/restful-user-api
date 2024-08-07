package com.project.api.restful_user_api.entity;

import com.project.api.restful_user_api.constant.RoleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

/**
 * Role entity to define users roles and implement authorization based on roles.
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "ROLE")
@Schema(title = "Role Entity",
        description = "The Role Model represents information about the users roles in api that use for authorization")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @CreationTimestamp //sets the field value to the current timestamp when the entity is first saved
    @Column(name = "CREATED_AT", updatable = false)
    private Date createdAt;
}