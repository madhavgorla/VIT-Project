package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.PayoutRequestDTO;
import com.coderabbits.fs2604.model.AuditLog;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.service.AuditService;
import com.coderabbits.fs2604.service.PayoutAuditIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/integration/anosh")
@Tag(name = "5. Database, Payout & Audit (Ganesh ↔ Anosh)", description = "Direct connection to Anosh's payout service, PostgreSQL synchronization, and audit trail")
public class AnoshPayoutIntegrationController {

    private final PayoutAuditIntegrationService payoutService;
    private final AuditService auditService;

    public AnoshPayoutIntegrationController(PayoutAuditIntegrationService payoutService,
                                            AuditService auditService) {
        this.payoutService = payoutService;
        this.auditService = auditService;
    }

    @PostMapping("/payout")
    @Operation(summary = "Process a parametric insurance payout", 
               description = "Disburses funds to farmer account/UPI and creates immutable audit record")
    public ResponseEntity<PayoutRecord> processPayout(@Valid @RequestBody PayoutRequestDTO request) {
        PayoutRecord record = payoutService.processPayout(request);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/payouts")
    @Operation(summary = "List all processed payouts", 
               description = "Fetches complete record of all parametric insurance disbursements")
    public ResponseEntity<List<PayoutRecord>> listPayouts() {
        return ResponseEntity.ok(payoutService.getAllPayouts());
    }

    @GetMapping("/payouts/{uuid}")
    @Operation(summary = "Get payout details by UUID", 
               description = "Retrieves specific disbursement transaction details")
    public ResponseEntity<PayoutRecord> getPayout(@PathVariable String uuid) {
        return payoutService.getPayoutByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/audit-logs")
    @Operation(summary = "Fetch complete audit trail", 
               description = "Immutable log of all system state changes, sync batches, triggers, and payouts")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditService.getAllLogs());
    }

    @GetMapping("/audit-logs/entity/{entityId}")
    @Operation(summary = "Fetch audit logs for an entity", 
               description = "Filters audit history for a specific farmer, policy, or payout UUID")
    public ResponseEntity<List<AuditLog>> getEntityAuditLogs(@PathVariable String entityId) {
        return ResponseEntity.ok(auditService.getLogsForEntity(entityId));
    }
}
