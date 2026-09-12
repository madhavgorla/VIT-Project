package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.PolicyThresholdResponseDTO;
import com.coderabbits.fs2604.dto.TriggerDecisionDTO;
import com.coderabbits.fs2604.model.TriggerEvent;
import com.coderabbits.fs2604.service.PolicyService;
import com.coderabbits.fs2604.service.TriggerEngineIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/integration/raja")
@Tag(name = "4. Rainfall & Trigger Engine (Ganesh ↔ Raja)", description = "Policy threshold sharing with Raja, and rainfall trigger result ingestion")
public class RajaTriggerIntegrationController {

    private final PolicyService policyService;
    private final TriggerEngineIntegrationService triggerEngineService;

    public RajaTriggerIntegrationController(PolicyService policyService,
                                            TriggerEngineIntegrationService triggerEngineService) {
        this.policyService = policyService;
        this.triggerEngineService = triggerEngineService;
    }

    @GetMapping("/thresholds")
    @Operation(summary = "Get policy thresholds for a district", 
               description = "Raja's IMD/CHIRPS Trigger Engine fetches parametric thresholds to evaluate incoming rainfall")
    public ResponseEntity<PolicyThresholdResponseDTO> getDistrictThresholds(@RequestParam String district) {
        PolicyThresholdResponseDTO summary = policyService.getThresholdSummaryForDistrict(district);
        return ResponseEntity.ok(summary);
    }

    @PostMapping("/trigger-result")
    @Operation(summary = "Ingest trigger decision from Raja's engine", 
               description = "Raja's engine posts rainfall data; if threshold is breached, automatic payouts are immediately triggered")
    public ResponseEntity<TriggerEvent> ingestTriggerResult(@Valid @RequestBody TriggerDecisionDTO decision) {
        TriggerEvent event = triggerEngineService.processTriggerDecision(decision);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/events")
    @Operation(summary = "List all parametric trigger events", 
               description = "Returns history of rainfall evaluations and triggered events")
    public ResponseEntity<List<TriggerEvent>> listEvents(@RequestParam(required = false) String district) {
        if (district != null && !district.isBlank()) {
            return ResponseEntity.ok(triggerEngineService.getRecentEventsForDistrict(district));
        }
        return ResponseEntity.ok(triggerEngineService.getAllTriggerEvents());
    }
}
