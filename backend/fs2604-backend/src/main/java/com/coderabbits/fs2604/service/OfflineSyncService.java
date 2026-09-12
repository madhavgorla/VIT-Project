package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.FarmerDTO;
import com.coderabbits.fs2604.dto.PolicyDTO;
import com.coderabbits.fs2604.dto.SyncBatchRequest;
import com.coderabbits.fs2604.dto.SyncBatchResponse;
import com.coderabbits.fs2604.dto.VoiceNotificationDTO;
import com.coderabbits.fs2604.model.Farmer;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.repository.FarmerRepository;
import com.coderabbits.fs2604.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfflineSyncService {

    private final FarmerService farmerService;
    private final PolicyService policyService;
    private final PayoutAuditIntegrationService payoutService;
    private final TextToSpeechFormatterService ttsService;
    private final AuditService auditService;
    private final FarmerRepository farmerRepository;
    private final PolicyRepository policyRepository;

    public OfflineSyncService(FarmerService farmerService,
                              PolicyService policyService,
                              PayoutAuditIntegrationService payoutService,
                              TextToSpeechFormatterService ttsService,
                              AuditService auditService,
                              FarmerRepository farmerRepository,
                              PolicyRepository policyRepository) {
        this.farmerService = farmerService;
        this.policyService = policyService;
        this.payoutService = payoutService;
        this.ttsService = ttsService;
        this.auditService = auditService;
        this.farmerRepository = farmerRepository;
        this.policyRepository = policyRepository;
    }

    /**
     * Core offline-first sync engine.
     * Ingests batched offline registrations & policies from Madhav's Flutter app,
     * resolves idempotency, and returns latest server state, payouts, and TTS audio alerts.
     */
    @Transactional
    public SyncBatchResponse processSyncBatch(SyncBatchRequest batch) {
        SyncBatchResponse response = new SyncBatchResponse();
        List<VoiceNotificationDTO> voiceList = new ArrayList<>();

        // 1. Process Offline Farmers
        if (batch.getOfflineFarmers() != null) {
            for (FarmerDTO farmerDto : batch.getOfflineFarmers()) {
                try {
                    Farmer savedFarmer = farmerService.registerFarmer(farmerDto);
                    response.getAcknowledgedFarmerUuids().add(savedFarmer.getFarmerUuid());
                } catch (Exception e) {
                    // Ignore duplicate key / handle safely
                }
            }
        }

        // 2. Process Offline Policies
        if (batch.getOfflinePolicies() != null) {
            for (PolicyDTO policyDto : batch.getOfflinePolicies()) {
                try {
                    Policy savedPolicy = policyService.createPolicy(policyDto);
                    response.getAcknowledgedPolicyUuids().add(savedPolicy.getPolicyUuid());

                    // Generate TTS voice confirmation for the newly synced policy
                    Optional<Farmer> farmerOpt = farmerRepository.findByFarmerUuid(savedPolicy.getFarmerUuid());
                    farmerOpt.ifPresent(farmer -> {
                        VoiceNotificationDTO voice = ttsService.createPolicyConfirmationVoice(farmer, savedPolicy);
                        voiceList.add(voice);
                    });
                } catch (Exception e) {
                    // Ignore duplicate key / handle safely
                }
            }
        }

        // 3. Fetch latest updated policies for the syncing farmer (or device)
        if (batch.getFarmerUuid() != null && !batch.getFarmerUuid().isBlank()) {
            List<Policy> farmerPolicies = policyRepository.findByFarmerUuid(batch.getFarmerUuid());
            response.setUpdatedPolicies(farmerPolicies);

            // 4. Fetch any pending payouts
            List<PayoutRecord> pendingPayouts = payoutService.getPendingMobileSyncPayouts(batch.getFarmerUuid());
            response.setPayouts(pendingPayouts);

            // Generate TTS voice alert for any new payouts
            Optional<Farmer> farmerOpt = farmerRepository.findByFarmerUuid(batch.getFarmerUuid());
            farmerOpt.ifPresent(farmer -> {
                for (PayoutRecord payout : pendingPayouts) {
                    VoiceNotificationDTO payoutVoice = ttsService.createPayoutVoice(farmer, payout, 18.5, 50.0);
                    voiceList.add(payoutVoice);
                }
            });

            // Mark payouts as acknowledged/synced to mobile
            payoutService.markPayoutsAsSynced(pendingPayouts);
        }

        response.setVoiceNotifications(voiceList);
        response.setMessage(String.format("Sync successful: %d farmers acknowledged, %d policies acknowledged, %d payouts delivered.",
                response.getAcknowledgedFarmerUuids().size(),
                response.getAcknowledgedPolicyUuids().size(),
                response.getPayouts().size()));

        auditService.log(
                "OFFLINE_SYNC_COMPLETED",
                "MADHAV_FLUTTER_APP",
                "SYNC_BATCH",
                batch.getDeviceId(),
                response.getMessage()
        );

        return response;
    }
}
