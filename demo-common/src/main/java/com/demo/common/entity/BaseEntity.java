package com.demo.common.entity;
import com.demo.common.util.UserContextHolder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@MappedSuperclass
public class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    @Column(nullable = false, updatable = false)
    private String creator;
    @Column(nullable = false, updatable = false)
    private LocalDateTime creation_time;
    @Column(nullable = false)
    private LocalDateTime modification_time;
    @NotBlank
    @Column(nullable = false)
    private String modifier;

    @PrePersist
    protected void onCreate() {
        creation_time = LocalDateTime.now();
        modification_time = LocalDateTime.now();
        creator = UserContextHolder.getUser().getUsername();
        modifier = UserContextHolder.getUser().getUsername();
    }

    @PreUpdate
    protected void onUpdate() {
        modification_time = LocalDateTime.now();
        modifier = UserContextHolder.getUser().getUsername();
    }
}
