package com.coderabbits.fs2604;

import com.coderabbits.fs2604.dto.FarmerDTO;
import com.coderabbits.fs2604.dto.PolicyDTO;
import com.coderabbits.fs2604.dto.SyncBatchRequest;
import com.coderabbits.fs2604.dto.SyncBatchResponse;
import com.coderabbits.fs2604.dto.TriggerDecisionDTO;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.model.PolicyStatus;
import com.coderabbits.fs2604.model.TriggerEvent;
import com.coderabbits.fs2604.model.TriggerOperator;
import com.coderabbits.fs2604.service.FarmerService;
import com.coderabbits.fs2604.service.OfflineSyncService;
import com.coderabbits.fs2604.service.PolicyService;
import com.coderabbits.fs2604.service.TriggerEngineIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Fs2604BackendApplicationTests {

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private OfflineSyncService offlineSyncService;

    @Autowired
    private TriggerEngineIntegrationService triggerEngineService;

    @Test
    void contextLoads() {
        assertNotNull(farmerService);
        assertNotNull(policyService);
    }

    @Test
    void testOfflineSyncBatchFromFlutterApp() {
        String testFarmerUuid = "offline-farmer-" + UUID.randomUUID();
        String testPolicyUuid = "offline-policy-" + UUID.randomUUID();

        // 1. Simulate Madhav's Flutter mobile app sending offline created items
        SyncBatchRequest request = new SyncBatchRequest();
        request.setDeviceId("device-redmi-note-11");
        request.setFarmerUuid(testFarmerUuid);

        FarmerDTO offlineFarmer = new FarmerDTO(
                testFarmerUuid,
                "999988887777",
                "Chandraiah",
                "9849012345",
                "Singanamala",
                "Anantapur",
                "Andhra Pradesh",
                "te", // Telugu
                "SBIN0009999",
                "chandraiah@upi"
        );
        request.getOfflineFarmers().add(offlineFarmer);

        PolicyDTO offlinePolicy = new PolicyDTO();
        offlinePolicy.setPolicyUuid(testPolicyUuid);
        offlinePolicy.setPolicyNumber("POL-OFFLINE-TEST-001");
        offlinePolicy.setFarmerUuid(testFarmerUuid);
        offlinePolicy.setCropType("Groundnut");
        offlinePolicy.setDistrict("Anantapur");
        offlinePolicy.setSeason("Kharif 2026");
        offlinePolicy.setCoverageAmount(20000.0);
        offlinePolicy.setPremiumAmount(600.0);
        offlinePolicy.setThresholdRainfallMm(45.0);
        offlinePolicy.setTriggerOperator(TriggerOperator.LESS_THAN);
        request.getOfflinePolicies().add(offlinePolicy);

        // 2. Process Sync Batch in Ganesh's backend
        SyncBatchResponse response = offlineSyncService.processSyncBatch(request);

        // 3. Verify acknowledgements
        assertTrue(response.isSuccess());
        assertTrue(response.getAcknowledgedFarmerUuids().contains(testFarmerUuid));
        assertTrue(response.getAcknowledgedPolicyUuids().contains(testPolicyUuid));
        assertFalse(response.getVoiceNotifications().isEmpty());
        assertEquals("te", response.getVoiceNotifications().get(0).getLanguageCode());
        assertTrue(response.getVoiceNotifications().get(0).getSpokenMessage().contains("Chandraiah"));
    }

    @Test
    void testParametricRainfallTriggerCascade() {
        // Trigger simulated rainfall reading of 10.0 mm in Anantapur (threshold is ~45-50 mm)
        TriggerDecisionDTO decision = new TriggerDecisionDTO();
        decision.setDistrict("Anantapur");
        decision.setReadingDate(LocalDate.now());
        decision.setMeasuredRainfallMm(10.0);
        decision.setSource("IMD_CHIRPS_COMBINED");
        decision.setTriggerOperator(TriggerOperator.LESS_THAN);

        TriggerEvent event = triggerEngineService.processTriggerDecision(decision);

        assertTrue(event.isTriggered());
        assertTrue(event.getAffectedPoliciesCount() > 0);

        // Check that policies were triggered and moved to PAID_OUT
        List<Policy> anantapurPolicies = policyService.getActivePoliciesInDistrict("Anantapur");
        // Because they were triggered, active policies count should be reduced or policies are PAID_OUT
        List<Policy> allPolicies = policyService.getAllPolicies();
        boolean hasPaidOut = allPolicies.stream().anyMatch(p -> p.getStatus() == PolicyStatus.PAID_OUT);
        assertTrue(hasPaidOut);
    }
}
