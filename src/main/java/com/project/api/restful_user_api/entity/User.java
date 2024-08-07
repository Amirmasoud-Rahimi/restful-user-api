package com.project.api.restful_user_api.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * User entity to define application users
 * We implement UserDetails interface to manage user details related to authentication
 * We extends RepresentationModel<T> to add hateoas to controller response
 *
 * @author Amirmasoud Rahimi
 * @since 1.0.0
 */
@Getter
@Setter
@Entity
@Table(name = "USERS") //Table USER is reserved in h2 database
@Schema(title = "User Entity",
        description = "The User Model represents information about the user who authenticates and uses the api")
public class User extends RepresentationModel<User> implements UserDetails {
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

    /**
     * For Logical Delete
     *
     * @since 1.0.0
     */
    @NotBlank
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @CreationTimestamp //sets the field value to the current timestamp when the entity is first saved
    @Column(name = "CREATED_AT", updatable = false)
    private Date createdAt;

    @UpdateTimestamp // sets the field value to the current timestamp on each entity update
    @Column(name = "UPDATED_AT")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role role;

    /**
     * Returns the user's roles list; it is helpful to manage permissions.
     *
     * @return Collection<? extends GrantedAuthority>
     * @since 1.0.0
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    /**
     * Returns field that is used as a username and is unique information about the user.
     *
     * @return username
     * @since 1.0.0
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}