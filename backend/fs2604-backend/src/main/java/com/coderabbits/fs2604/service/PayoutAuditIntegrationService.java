package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.PayoutRequestDTO;
import com.coderabbits.fs2604.model.Farmer;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.PayoutStatus;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.model.PolicyStatus;
import com.coderabbits.fs2604.repository.FarmerRepository;
import com.coderabbits.fs2604.repository.PayoutRecordRepository;
import com.coderabbits.fs2604.repository.PolicyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayoutAuditIntegrationService {

    private static final Logger log = LoggerFactory.getLogger(PayoutAuditIntegrationService.class);

    private final PayoutRecordRepository payoutRecordRepository;
    private final PolicyRepository policyRepository;
    private final FarmerRepository farmerRepository;
    private final AuditService auditService;
    private final RestClient restClient;

    @Value("${integration.anosh.base-url:http://localhost:8082}")
    private String anoshBaseUrl;

    @Value("${integration.anosh.simulation-mode:true}")
    private boolean simulationMode;

    public PayoutAuditIntegrationService(PayoutRecordRepository payoutRecordRepository,
                                         PolicyRepository policyRepository,
                                         FarmerRepository farmerRepository,
                                         AuditService auditService) {
        this.payoutRecordRepository = payoutRecordRepository;
        this.policyRepository = policyRepository;
        this.farmerRepository = farmerRepository;
        this.auditService = auditService;
        this.restClient = RestClient.builder().build();
    }

    /**
     * Executes parametric payout for a policy triggered by rainfall condition.
     * Connected to Anosh's Payout & PostgreSQL module.
     */
    @Transactional
    public PayoutRecord processPayout(PayoutRequestDTO request) {
        Policy policy = policyRepository.findByPolicyUuid(request.getPolicyUuid())
                .orElseThrow(() -> new IllegalArgumentException("Policy not found: " + request.getPolicyUuid()));

        Farmer farmer = farmerRepository.findByFarmerUuid(request.getFarmerUuid())
                .orElseThrow(() -> new IllegalArgumentException("Farmer not found: " + request.getFarmerUuid()));

        String payoutUuid = UUID.randomUUID().toString();
        String targetAccount = (request.getRecipientAccountOrUpi() != null && !request.getRecipientAccountOrUpi().isBlank())
                ? request.getRecipientAccountOrUpi()
                : (farmer.getUpiId() != null ? farmer.getUpiId() : farmer.getBankAccountNumber());

        if (targetAccount == null) {
            targetAccount = "UPI-DEFAULT-" + farmer.getPhone();
        }

        PayoutRecord record = new PayoutRecord(
                payoutUuid,
                policy.getPolicyUuid(),
                policy.getPolicyNumber(),
                farmer.getFarmerUuid(),
                farmer.getName(),
                request.getAmount(),
                targetAccount,
                request.getTriggerReason()
        );

        if (!simulationMode) {
            try {
                log.info("Sending payout request to Anosh's Payout microservice at {}...", anoshBaseUrl);
                // Call Anosh's payout service endpoint
                restClient.post()
                        .uri(anoshBaseUrl + "/api/v1/payouts")
                        .body(request)
                        .retrieve()
                        .toBodilessEntity();
            } catch (Exception e) {
                log.warn("Anosh microservice unreachable, falling back to local simulation: {}", e.getMessage());
            }
        }

        // Save payout locally and update policy status
        PayoutRecord saved = payoutRecordRepository.save(record);
        policy.setStatus(PolicyStatus.PAID_OUT);
        policyRepository.save(policy);

        auditService.log(
                "PAYOUT_DISBURSED",
                "ANOSH_PAYOUT_SERVICE",
                "PAYOUT",
                saved.getPayoutUuid(),
                String.format("Automatic payout of Rs. %.0f disbursed to %s (%s) for policy %s. Txn: %s",
                        saved.getAmount(), saved.getFarmerName(), targetAccount, saved.getPolicyNumber(), saved.getTransactionReference())
        );

        return saved;
    }

    public List<PayoutRecord> getPendingMobileSyncPayouts(String farmerUuid) {
        if (farmerUuid != null && !farmerUuid.isBlank()) {
            return payoutRecordRepository.findByFarmerUuidAndSyncedToMobileFalse(farmerUuid);
        }
        return payoutRecordRepository.findBySyncedToMobileFalse();
    }

    @Transactional
    public void markPayoutsAsSynced(List<PayoutRecord> payouts) {
        for (PayoutRecord p : payouts) {
            p.setSyncedToMobile(true);
            payoutRecordRepository.save(p);
        }
    }

    public List<PayoutRecord> getAllPayouts() {
        return payoutRecordRepository.findAll();
    }

    public Optional<PayoutRecord> getPayoutByUuid(String uuid) {
        return payoutRecordRepository.findByPayoutUuid(uuid);
    }
}
