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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "USERS") //Table USER is reserved in h2 database
//implements UserDetails To manage user details related to authentication
public class User implements UserDetails {
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
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false)
    private Role role;

    //returns the user's roles list; it is helpful to manage permissions.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        // return field that is used as a username and is unique information about the user.
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