package com.project.api.restful_user_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USERS") //Table USER is reserved in h2 database
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)// automatically generate the primary key value
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Size(min = 3, max = 128)
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Email
    @NotBlank
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "IS_ACTIVE", nullable = false) //for Logical Delete
    private Boolean isActive;

    @CreationTimestamp //sets the field value to the current timestamp when the entity is first saved
    @Column(name = "CREATED_AT", updatable = false)
    private Date createdAt;

    @UpdateTimestamp // sets the field value to the current timestamp on each entity update
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID",referencedColumnName = "ID", nullable = false)
    private Role role;
}