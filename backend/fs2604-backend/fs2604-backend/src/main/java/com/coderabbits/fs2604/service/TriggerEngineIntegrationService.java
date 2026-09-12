package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.PayoutRequestDTO;
import com.coderabbits.fs2604.dto.TriggerDecisionDTO;
import com.coderabbits.fs2604.model.*;
import com.coderabbits.fs2604.repository.PolicyRepository;
import com.coderabbits.fs2604.repository.TriggerEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TriggerEngineIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(TriggerEngineIntegrationService.class);

    private final PolicyRepository policyRepository;
    private final TriggerEventRepository triggerEventRepository;
    private final PayoutAuditIntegrationService payoutAuditService;
    private final AuditService auditService;
    private final RestClient restClient;

    @Value("${integration.raja.base-url:http://localhost:8081}")
    private String rajaBaseUrl;

    @Value("${integration.raja.simulation-mode:true}")
    private boolean simulationMode;

    public TriggerEngineIntegrationService(PolicyRepository policyRepository,
                                           TriggerEventRepository triggerEventRepository,
                                           PayoutAuditIntegrationService payoutAuditService,
                                           AuditService auditService) {
        this.policyRepository = policyRepository;
        this.triggerEventRepository = triggerEventRepository;
        this.payoutAuditService = payoutAuditService;
        this.auditService = auditService;
        this.restClient = RestClient.builder().build();
    }

    /**
     * Ingests a rainfall reading from Raja's IMD/CHIRPS trigger engine,
     * compares with policies in the district, and fires automated payouts!
     */
    @Transactional
    public TriggerEvent processTriggerDecision(TriggerDecisionDTO decision) {
        List<Policy> activePolicies = policyRepository.findByDistrictIgnoreCaseAndStatus(
                decision.getDistrict(), PolicyStatus.ACTIVE);

        List<Policy> triggeredPolicies = new ArrayList<>();

        for (Policy policy : activePolicies) {
            boolean conditionMet = false;
            if (policy.getTriggerOperator() == TriggerOperator.LESS_THAN) {
                // Drought trigger: rainfall below threshold
                if (decision.getMeasuredRainfallMm() < policy.getThresholdRainfallMm()) {
                    conditionMet = true;
                }
            } else if (policy.getTriggerOperator() == TriggerOperator.GREATER_THAN) {
                // Excess rainfall / flood trigger: rainfall above threshold
                if (decision.getMeasuredRainfallMm() > policy.getThresholdRainfallMm()) {
                    conditionMet = true;
                }
            }

            if (conditionMet) {
                triggeredPolicies.add(policy);
            }
        }

        boolean isTriggered = !triggeredPolicies.isEmpty();
        double avgThreshold = activePolicies.stream()
                .mapToDouble(Policy::getThresholdRainfallMm)
                .average()
                .orElse(decision.getThresholdRainfallMm() != null ? decision.getThresholdRainfallMm() : 0.0);

        TriggerEvent event = new TriggerEvent(
                UUID.randomUUID().toString(),
                decision.getDistrict(),
                decision.getReadingDate(),
                decision.getMeasuredRainfallMm(),
                avgThreshold,
                decision.getTriggerOperator(),
                isTriggered,
                decision.getSource() != null ? decision.getSource() : "IMD",
                triggeredPolicies.size(),
                decision.getNotes() != null ? decision.getNotes() : "Parametric threshold comparison completed"
        );

        TriggerEvent savedEvent = triggerEventRepository.save(event);

        auditService.log(
                isTriggered ? "PARAMETRIC_TRIGGER_FIRED" : "RAINFALL_EVALUATED_NORMAL",
                "RAJA_TRIGGER_ENGINE",
                "TRIGGER_EVENT",
                savedEvent.getEventUuid(),
                String.format("District: %s | Rainfall: %.1f mm | Threshold: %.1f mm | Triggered: %b | Affected Policies: %d",
                        decision.getDistrict(), decision.getMeasuredRainfallMm(), avgThreshold, isTriggered, triggeredPolicies.size())
        );

        // For all triggered policies, immediately execute automatic payouts (Raja -> Anosh)
        for (Policy triggeredPolicy : triggeredPolicies) {
            triggeredPolicy.setStatus(PolicyStatus.TRIGGERED);
            policyRepository.save(triggeredPolicy);

            String reason = String.format("Parametric Trigger: %s rainfall of %.1f mm breached threshold of %.1f mm in %s",
                    triggeredPolicy.getTriggerOperator() == TriggerOperator.LESS_THAN ? "Drought deficit" : "Flood excess",
                    decision.getMeasuredRainfallMm(),
                    triggeredPolicy.getThresholdRainfallMm(),
                    decision.getDistrict());

            PayoutRequestDTO payoutRequest = new PayoutRequestDTO(
                    triggeredPolicy.getPolicyUuid(),
                    triggeredPolicy.getFarmerUuid(),
                    triggeredPolicy.getCoverageAmount(),
                    reason,
                    null
            );

            payoutAuditService.processPayout(payoutRequest);
        }

        return savedEvent;
    }

    public List<TriggerEvent> getAllTriggerEvents() {
        return triggerEventRepository.findAll();
    }

    public List<TriggerEvent> getRecentEventsForDistrict(String district) {
        return triggerEventRepository.findByDistrictIgnoreCaseOrderByReadingDateDesc(district);
    }
}
