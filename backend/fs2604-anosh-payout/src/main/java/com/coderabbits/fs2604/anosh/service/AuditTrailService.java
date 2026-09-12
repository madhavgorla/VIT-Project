package com.coderabbits.fs2604.anosh.service;

import com.coderabbits.fs2604.anosh.model.ImmutableAuditRecord;
import com.coderabbits.fs2604.anosh.repository.ImmutableAuditRecordRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;

@Service
public class AuditTrailService {

    private final ImmutableAuditRecordRepository auditRecordRepository;

    public AuditTrailService(ImmutableAuditRecordRepository auditRecordRepository) {
        this.auditRecordRepository = auditRecordRepository;
    }

    public ImmutableAuditRecord recordAudit(String action, String actor, String entityId, String details) {
        String auditUuid = UUID.randomUUID().toString();
        String integrityHash = generateHash(auditUuid, action, actor, entityId, details);

        ImmutableAuditRecord record = new ImmutableAuditRecord(
                auditUuid,
                action,
                actor,
                entityId,
                details,
                integrityHash
        );

        return auditRecordRepository.save(record);
    }

    public List<ImmutableAuditRecord> getAllAuditRecords() {
        return auditRecordRepository.findAllByOrderByTimestampDesc();
    }

    public List<ImmutableAuditRecord> getAuditRecordsForEntity(String entityId) {
        return auditRecordRepository.findByEntityIdOrderByTimestampDesc(entityId);
    }

    private String generateHash(String uuid, String action, String actor, String entityId, String details) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String raw = String.format("%s:%s:%s:%s:%s", uuid, action, actor, entityId, details);
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            return "HASH_UNAVAILABLE";
        }
    }
}
