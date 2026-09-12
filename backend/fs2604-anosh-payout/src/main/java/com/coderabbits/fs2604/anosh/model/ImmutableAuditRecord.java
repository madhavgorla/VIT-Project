package com.coderabbits.fs2604.anosh.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "immutable_audit_records")
public class ImmutableAuditRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audit_uuid", unique = true, nullable = false)
    private String auditUuid;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "actor", nullable = false)
    private String actor;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "details", length = 1000)
    private String details;

    @Column(name = "integrity_hash")
    private String integrityHash;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public ImmutableAuditRecord() {
    }

    public ImmutableAuditRecord(String auditUuid, String action, String actor, 
                                String entityId, String details, String integrityHash) {
        this.auditUuid = auditUuid;
        this.action = action;
        this.actor = actor;
        this.entityId = entityId;
        this.details = details;
        this.integrityHash = integrityHash;
        this.timestamp = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditUuid() {
        return auditUuid;
    }

    public void setAuditUuid(String auditUuid) {
        this.auditUuid = auditUuid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIntegrityHash() {
        return integrityHash;
    }

    public void setIntegrityHash(String integrityHash) {
        this.integrityHash = integrityHash;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
