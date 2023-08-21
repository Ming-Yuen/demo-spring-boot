package com.demo.common.entity;
import com.demo.common.util.UserContextHolder;
import lombok.Getter;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(nullable = false, updatable = false)
    private String creator;
    @Column(nullable = false, updatable = false)
    private OffsetDateTime creation_time;
    @NotBlank
    @Column(nullable = false)
    private String modifier;
    @Column(nullable = false)
    private OffsetDateTime modification_time;

    @PrePersist
    protected void onCreate() {
        creation_time = OffsetDateTime.now();
        modification_time = OffsetDateTime.now();
        creator = UserContextHolder.getUser().getUsername();
        modifier = UserContextHolder.getUser().getUsername();
    }

    @PreUpdate
    protected void onUpdate() {
        modification_time = OffsetDateTime.now();
        modifier = UserContextHolder.getUser().getUsername();
    }
}
