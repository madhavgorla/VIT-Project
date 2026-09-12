package com.coderabbits.fs2604.raja.service;

import com.coderabbits.fs2604.raja.dto.DistrictThresholdDTO;
import com.coderabbits.fs2604.raja.model.RainfallObservation;
import com.coderabbits.fs2604.raja.model.TriggerDecisionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class TriggerEvaluationEngine {

    private static final Logger log = LoggerFactory.getLogger(TriggerEvaluationEngine.class);

    private final ImdChirpsIngestionService ingestionService;
    private final RestClient restClient;
    private final List<TriggerDecisionResult> decisionHistory = new CopyOnWriteArrayList<>();

    @Value("${integration.ganesh.base-url:http://localhost:8080}")
    private String ganeshBaseUrl;

    @Value("${integration.anosh.base-url:http://localhost:8082}")
    private String anoshBaseUrl;

    public TriggerEvaluationEngine(ImdChirpsIngestionService ingestionService) {
        this.ingestionService = ingestionService;
        this.restClient = RestClient.builder().build();
    }

    /**
     * Evaluates rainfall for a district against active policies fetched from Ganesh's Backend.
     * When triggered, sends payout executions to Anosh (Port 8082) and notifies Ganesh (Port 8080).
     */
    public TriggerDecisionResult evaluateDistrict(String district, Double customRainfallMm) {
        // 1. Determine rainfall to evaluate
        double rainfall = (customRainfallMm != null)
                ? customRainfallMm
                : ingestionService.getLatestObservation(district)
                        .map(RainfallObservation::getRainfallMm)
                        .orElse(20.0);

        // 2. Fetch Policy Thresholds from Ganesh's Backend API (Port 8080)
        DistrictThresholdDTO thresholdSummary = null;
        try {
            log.info("Fetching policy thresholds for district '{}' from Ganesh at {}...", district, ganeshBaseUrl);
            thresholdSummary = restClient.get()
                    .uri(ganeshBaseUrl + "/api/v1/integration/raja/thresholds?district=" + district)
                    .retrieve()
                    .body(DistrictThresholdDTO.class);
        } catch (Exception e) {
            log.warn("Could not reach Ganesh's Backend, using fallback threshold of 45.0 mm: {}", e.getMessage());
        }

        double threshold = (thresholdSummary != null && thresholdSummary.getAverageThresholdMm() != null && thresholdSummary.getAverageThresholdMm() > 0)
                ? thresholdSummary.getAverageThresholdMm()
                : 45.0;

        // 3. Parametric Decision Logic (Drought: measured < threshold)
        boolean isTriggered = rainfall < threshold;
        String decisionId = "DEC-" + System.currentTimeMillis();
        String notes = isTriggered
                ? String.format("PARAMETRIC TRIGGER FIRED: Rainfall of %.1f mm is below threshold %.1f mm in %s", rainfall, threshold, district)
                : String.format("NORMAL: Rainfall of %.1f mm meets threshold %.1f mm in %s", rainfall, threshold, district);

        TriggerDecisionResult result = new TriggerDecisionResult(
                decisionId,
                district,
                LocalDate.now(),
                rainfall,
                threshold,
                "LESS_THAN",
                isTriggered,
                "IMD_CHIRPS_COMBINED",
                notes
        );
        decisionHistory.add(0, result);

        if (isTriggered) {
            // 4. Send Trigger Result to Ganesh's Backend (Port 8080)
            try {
                log.info("Emitting trigger result to Ganesh Backend API at {}...", ganeshBaseUrl);
                Map<String, Object> ganeshPayload = Map.of(
                        "district", district,
                        "readingDate", LocalDate.now().toString(),
                        "measuredRainfallMm", rainfall,
                        "thresholdRainfallMm", threshold,
                        "triggerOperator", "LESS_THAN",
                        "triggered", true,
                        "source", "IMD_CHIRPS_COMBINED",
                        "notes", notes
                );
                restClient.post()
                        .uri(ganeshBaseUrl + "/api/v1/integration/raja/trigger-result")
                        .body(ganeshPayload)
                        .retrieve()
                        .toBodilessEntity();
            } catch (Exception e) {
                log.warn("Ganesh Backend unreachable during trigger post: {}", e.getMessage());
            }

            // 5. Send Direct Payout Execution to Anosh's Payout Service (Port 8082)
            try {
                log.info("Emitting payout settlement request to Anosh Payout Service at {}...", anoshBaseUrl);
                Map<String, Object> anoshPayload = Map.of(
                        "policyUuid", "POL-" + district.toUpperCase() + "-AUTO",
                        "farmerUuid", "FARMER-" + district.toUpperCase() + "-ALL",
                        "amount", 10000.0,
                        "recipientAccountOrUpi", "AUTO-SETTLE-" + district.toUpperCase(),
                        "triggerReason", notes
                );
                restClient.post()
                        .uri(anoshBaseUrl + "/api/v1/payouts/execute")
                        .body(anoshPayload)
                        .retrieve()
                        .toBodilessEntity();
            } catch (Exception e) {
                log.warn("Anosh Payout Service unreachable during payout execution: {}", e.getMessage());
            }
        }

        return result;
    }

    public List<TriggerDecisionResult> getHistory() {
        return decisionHistory;
    }
}
