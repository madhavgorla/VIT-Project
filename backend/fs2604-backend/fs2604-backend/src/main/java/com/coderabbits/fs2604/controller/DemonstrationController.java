package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.TriggerDecisionDTO;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.model.TriggerEvent;
import com.coderabbits.fs2604.model.TriggerOperator;
import com.coderabbits.fs2604.service.PayoutAuditIntegrationService;
import com.coderabbits.fs2604.service.PolicyService;
import com.coderabbits.fs2604.service.TriggerEngineIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/demo")
@Tag(name = "6. Hackathon Live Demo (Team Code Rabbits)", description = "Interactive 1-click end-to-end parametric simulation for judges and teammates")
public class DemonstrationController {

    private final TriggerEngineIntegrationService triggerEngineService;
    private final PolicyService policyService;
    private final PayoutAuditIntegrationService payoutService;

    public DemonstrationController(TriggerEngineIntegrationService triggerEngineService,
                                   PolicyService policyService,
                                   PayoutAuditIntegrationService payoutService) {
        this.triggerEngineService = triggerEngineService;
        this.policyService = policyService;
        this.payoutService = payoutService;
    }

    @PostMapping("/simulate-drought")
    @Operation(summary = "Simulate Drought Trigger (Hackathon Pitch Demo)", 
               description = "Simulates severe rainfall deficit in Anantapur (12.5mm vs 50mm threshold). Demonstrates immediate automatic cascade across all 4 modules.")
    public ResponseEntity<Map<String, Object>> simulateDrought(
            @RequestParam(defaultValue = "Anantapur") String district,
            @RequestParam(defaultValue = "12.5") Double rainfallMm) {

        TriggerDecisionDTO decision = new TriggerDecisionDTO();
        decision.setDistrict(district);
        decision.setReadingDate(LocalDate.now());
        decision.setMeasuredRainfallMm(rainfallMm);
        decision.setThresholdRainfallMm(50.0);
        decision.setTriggerOperator(TriggerOperator.LESS_THAN);
        decision.setSource("IMD_AUTOMATED_WEATHER_STATION");
        decision.setNotes("Severe rainfall deficit detected during critical crop vegetative stage");

        TriggerEvent event = triggerEngineService.processTriggerDecision(decision);
        List<Policy> districtPolicies = policyService.getActivePoliciesInDistrict(district);
        List<PayoutRecord> recentPayouts = payoutService.getAllPayouts();

        Map<String, Object> demoSummary = new HashMap<>();
        demoSummary.put("step1_farmer_mobile", "Farmer previously enrolled via Madhav's Flutter App");
        demoSummary.put("step2_ganesh_api", "Ganesh's backend registered policy and published threshold to Raja's engine");
        demoSummary.put("step3_raja_trigger", Map.of(
                "source", event.getSource(),
                "district", event.getDistrict(),
                "measuredRainfallMm", event.getMeasuredRainfallMm(),
                "thresholdMm", event.getThresholdRainfallMm(),
                "conditionMet", event.isTriggered(),
                "affectedPolicies", event.getAffectedPoliciesCount()
        ));
        demoSummary.put("step4_anosh_payout_audit", "Automated payout initiated and audit trail recorded");
        demoSummary.put("step5_voice_notification", "Voice alert prepared in farmer's preferred language (Telugu/Hindi) for next Flutter background sync!");
        demoSummary.put("triggerEvent", event);
        demoSummary.put("latestPayouts", recentPayouts);

        return ResponseEntity.ok(demoSummary);
    }
}
