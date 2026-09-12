package com.coderabbits.fs2604.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "audit_uuid", unique = true, nullable = false)
    private String auditUuid;

    @Column(name = "action", nullable = false)
    private String action; // E.g., FARMER_REGISTERED, OFFLINE_SYNC, TRIGGER_FIRED, PAYOUT_DISBURSED

    @Column(name = "actor", nullable = false)
    private String actor; // "MADHAV_APP", "GANESH_API", "RAJA_TRIGGER", "ANOSH_PAYOUT"

    @Column(name = "entity_type", nullable = false)
    private String entityType; // "FARMER", "POLICY", "TRIGGER_EVENT", "PAYOUT"

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "details", length = 2000)
    private String details;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public AuditLog() {
    }

    public AuditLog(String auditUuid, String action, String actor, String entityType, 
                    String entityId, String details) {
        this.auditUuid = auditUuid;
        this.action = action;
        this.actor = actor;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
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

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
