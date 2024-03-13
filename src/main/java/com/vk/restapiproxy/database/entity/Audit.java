package com.vk.restapiproxy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "audit")
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("JpaDataSourceORMInspection")
public class Audit {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "hasAccess", nullable = false)
    private boolean hasAccess;

    @Enumerated(EnumType.STRING)
    private RequestMethod requestMethod;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "resource_type_id", referencedColumnName = "id")
    private ResourceType resourceType;

    @Column(name = "requestParams")
    private String requestParams;

    @CreationTimestamp
    private Date creationTime;

    @SuppressWarnings("unused")
    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
}
