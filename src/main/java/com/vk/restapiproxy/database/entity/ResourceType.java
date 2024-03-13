package com.vk.restapiproxy.database.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Table
@Entity
@Getter
@Setter
public class ResourceType {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    @Getter
    public enum Name {
        POSTS("posts"),
        USERS("users"),
        ALBUMS("albums");

        private final String name;

        Name(String name) {
            this.name = name;
        }
    }
}
