package com.demo.common.entity;

import com.demo.common.util.ContextHolder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class UserBaseEntity implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "version", nullable = false)
    private Integer tx_version;
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
    @NotBlank
    @Column(name = "update_by", nullable = false)
    private String updatedBy;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        tx_version = 1;
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        createdBy = StringUtils.defaultIfBlank(createdBy, ContextHolder.getUser() == null ? null : ContextHolder.getUser().getUserName());
        updatedBy = StringUtils.defaultIfBlank(updatedBy, ContextHolder.getUser() == null ? null : ContextHolder.getUser().getUserName());
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
        updatedBy = StringUtils.defaultIfBlank(updatedBy, ContextHolder.getUser() == null ? null : ContextHolder.getUser().getUserName());
    }
}
