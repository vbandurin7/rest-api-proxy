package com.vk.restapiproxy.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Table
@Entity
@Getter
@Setter
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    @Override
    public String getAuthority() {
        return name.name();
    }

    public enum Name {
        ROLE_ADMIN,
        ROLE_POSTS_VIEWER,
        ROLE_POSTS_EDITOR,
        ROLE_USERS_VIEWER,
        ROLE_USERS_EDITOR,
        ROLE_ALBUMS_VIEWER,
        ROLE_ALBUMS_EDITOR,
    }
}
