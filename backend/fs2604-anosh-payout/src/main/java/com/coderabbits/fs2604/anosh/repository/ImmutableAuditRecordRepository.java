package com.coderabbits.fs2604.anosh.repository;

import com.coderabbits.fs2604.anosh.model.ImmutableAuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImmutableAuditRecordRepository extends JpaRepository<ImmutableAuditRecord, Long> {
    List<ImmutableAuditRecord> findByEntityIdOrderByTimestampDesc(String entityId);
    List<ImmutableAuditRecord> findAllByOrderByTimestampDesc();
}
