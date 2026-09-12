package com.coderabbits.fs2604.anosh.controller;

import com.coderabbits.fs2604.anosh.model.ImmutableAuditRecord;
import com.coderabbits.fs2604.anosh.service.AuditTrailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit")
@Tag(name = "Anosh - Audit & Compliance", description = "Cryptographically verifiable immutable audit records")
public class AuditController {

    private final AuditTrailService auditTrailService;

    public AuditController(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    @GetMapping("/records")
    @Operation(summary = "Get all audit records", description = "Fetches complete immutable audit history")
    public ResponseEntity<List<ImmutableAuditRecord>> getAllRecords() {
        return ResponseEntity.ok(auditTrailService.getAllAuditRecords());
    }

    @GetMapping("/records/{entityId}")
    @Operation(summary = "Get audit records for entity", description = "Fetches audit records for a transaction or policy")
    public ResponseEntity<List<ImmutableAuditRecord>> getRecordsForEntity(@PathVariable String entityId) {
        return ResponseEntity.ok(auditTrailService.getAuditRecordsForEntity(entityId));
    }
}
