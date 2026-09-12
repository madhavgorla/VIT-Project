package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.PayoutRequestDTO;
import com.coderabbits.fs2604.dto.TriggerDecisionDTO;
import com.coderabbits.fs2604.dto.VoiceNotificationDTO;
import com.coderabbits.fs2604.model.*;
import com.coderabbits.fs2604.repository.FarmerRepository;
import com.coderabbits.fs2604.service.PayoutAuditIntegrationService;
import com.coderabbits.fs2604.service.PolicyService;
import com.coderabbits.fs2604.service.TextToSpeechFormatterService;
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
    private final FarmerRepository farmerRepository;
    private final TextToSpeechFormatterService ttsService;

    public DemonstrationController(TriggerEngineIntegrationService triggerEngineService,
                                   PolicyService policyService,
                                   PayoutAuditIntegrationService payoutService,
                                   FarmerRepository farmerRepository,
                                   TextToSpeechFormatterService ttsService) {
        this.triggerEngineService = triggerEngineService;
        this.policyService = policyService;
        this.payoutService = payoutService;
        this.farmerRepository = farmerRepository;
        this.ttsService = ttsService;
    }

    @PostMapping("/send-rainfall-relief")
    @Operation(summary = "Send Rs. 10,000 Emergency Rainfall Relief Payout", 
               description = "Disburses an immediate emergency relief payout of Rs. 10,000 directly from the backend to the user/farmer suffering from rainfall deficit trouble.")
    public ResponseEntity<Map<String, Object>> sendRainfallRelief(
            @RequestParam(defaultValue = "f1-anantapur-uuid-001") String farmerUuid,
            @RequestParam(defaultValue = "10000.0") Double amount,
            @RequestParam(defaultValue = "Severe rainfall deficit / crop trouble in Anantapur: Emergency parametric relief payout") String reason) {

        Farmer farmer = farmerRepository.findByFarmerUuid(farmerUuid)
                .orElse(farmerRepository.findAll().stream().findFirst().orElseThrow(() -> new IllegalStateException("No farmer registered")));

        List<Policy> farmerPolicies = policyService.getByFarmerUuid(farmer.getFarmerUuid());
        String policyUuid = !farmerPolicies.isEmpty() ? farmerPolicies.get(0).getPolicyUuid() : "POL-EMERGENCY-RELIEF-001";

        PayoutRequestDTO payoutRequest = new PayoutRequestDTO(
                policyUuid,
                farmer.getFarmerUuid(),
                amount != null ? amount : 10000.0,
                reason,
                farmer.getUpiId() != null ? farmer.getUpiId() : farmer.getBankAccountNumber()
        );

        // Execute payout through Anosh's settlement service & audit ledger
        PayoutRecord record = payoutService.processPayout(payoutRequest);

        // Generate immediate regional TTS alert for Madhav's mobile app
        VoiceNotificationDTO voiceNotification = ttsService.createRainfallTroubleReliefVoice(farmer, record.getAmount(), record.getTransactionReference());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", "Emergency relief payout of Rs. " + record.getAmount() + " successfully dispatched to " + farmer.getName());
        response.put("disbursedAmount", record.getAmount());
        response.put("farmerName", farmer.getName());
        response.put("district", farmer.getDistrict());
        response.put("targetUpiOrAccount", record.getRecipientAccountOrUpi());
        response.put("transactionId", record.getTransactionReference());
        response.put("voiceNotificationTTS", voiceNotification);

        return ResponseEntity.ok(response);
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
