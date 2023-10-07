package com.demo.admin.entity;

import com.demo.admin.util.UserContextHolder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, updatable = false)
    private Integer txVersion;
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
        txVersion = 1;
        creation_time = OffsetDateTime.now();
        modification_time = OffsetDateTime.now();
        creator = StringUtils.defaultIfBlank(creator, UserContextHolder.getUser() == null ? null : UserContextHolder.getUser().getUserName());
        modifier = StringUtils.defaultIfBlank(modifier, UserContextHolder.getUser() == null ? null : UserContextHolder.getUser().getUserName());
    }

    @PreUpdate
    protected void onUpdate() {
        modification_time = OffsetDateTime.now();
        modifier = StringUtils.defaultIfBlank(modifier, UserContextHolder.getUser() == null ? null : UserContextHolder.getUser().getUserName());
    }
}
