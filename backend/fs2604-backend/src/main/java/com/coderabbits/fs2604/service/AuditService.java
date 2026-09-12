package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.model.AuditLog;
import com.coderabbits.fs2604.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog log(String action, String actor, String entityType, String entityId, String details) {
        AuditLog entry = new AuditLog(
                UUID.randomUUID().toString(),
                action,
                actor,
                entityType,
                entityId,
                details
        );
        return auditLogRepository.save(entry);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAllByOrderByTimestampDesc();
    }

    public List<AuditLog> getLogsForEntity(String entityId) {
        return auditLogRepository.findByEntityIdOrderByTimestampDesc(entityId);
    }
}
