package com.coderabbits.fs2604.repository;

import com.coderabbits.fs2604.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityIdOrderByTimestampDesc(String entityId);
    List<AuditLog> findAllByOrderByTimestampDesc();
}
