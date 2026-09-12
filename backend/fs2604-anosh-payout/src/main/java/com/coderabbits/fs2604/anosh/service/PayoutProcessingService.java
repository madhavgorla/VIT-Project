package com.coderabbits.fs2604.anosh.service;

import com.coderabbits.fs2604.anosh.dto.PayoutExecutionRequest;
import com.coderabbits.fs2604.anosh.dto.PayoutExecutionResponse;
import com.coderabbits.fs2604.anosh.model.PayoutTransaction;
import com.coderabbits.fs2604.anosh.repository.PayoutTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class PayoutProcessingService {

    private static final Logger log = LoggerFactory.getLogger(PayoutProcessingService.class);

    private final PayoutTransactionRepository payoutRepository;
    private final AuditTrailService auditTrailService;
    private final RestClient restClient;

    @Value("${integration.ganesh.base-url:http://localhost:8080}")
    private String ganeshBaseUrl;

    public PayoutProcessingService(PayoutTransactionRepository payoutRepository, 
                                   AuditTrailService auditTrailService) {
        this.payoutRepository = payoutRepository;
        this.auditTrailService = auditTrailService;
        this.restClient = RestClient.builder().build();
    }

    @Transactional
    public PayoutExecutionResponse processPayout(PayoutExecutionRequest request) {
        String txnId = "TXN-ANOSH-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 9000 + 1000);

        Double payoutAmount = (request.getAmount() != null && request.getAmount() > 0) ? request.getAmount() : 10000.0;
        String policyUuid = (request.getPolicyUuid() != null && !request.getPolicyUuid().isBlank() && !request.getPolicyUuid().equalsIgnoreCase("string"))
                ? request.getPolicyUuid()
                : "pol-groundnut-anantapur-001";
        String farmerUuid = (request.getFarmerUuid() != null && !request.getFarmerUuid().isBlank() && !request.getFarmerUuid().equalsIgnoreCase("string"))
                ? request.getFarmerUuid()
                : "f1-anantapur-uuid-001";
        String recipient = (request.getRecipientAccountOrUpi() != null && !request.getRecipientAccountOrUpi().isBlank() && !request.getRecipientAccountOrUpi().equalsIgnoreCase("string"))
                ? request.getRecipientAccountOrUpi()
                : "ramesh.naidu@ybl";
        String triggerReason = (request.getTriggerReason() != null && !request.getTriggerReason().isBlank() && !request.getTriggerReason().equalsIgnoreCase("string"))
                ? request.getTriggerReason()
                : "Severe rainfall deficit / crop trouble: Automated parametric relief payout of Rs. 10,000";

        PayoutTransaction transaction = new PayoutTransaction(
                txnId,
                policyUuid,
                farmerUuid,
                payoutAmount,
                recipient,
                triggerReason
        );

        PayoutTransaction saved = payoutRepository.save(transaction);

        // Record tamper-evident cryptographic audit trail
        auditTrailService.recordAudit(
                "PARAMETRIC_PAYOUT_SETTLED",
                "ANOSH_PAYOUT_ENGINE",
                saved.getTransactionId(),
                String.format("Disbursed Rs. %.2f for Policy %s to Account %s. Reason: %s",
                        saved.getAmount(), saved.getPolicyUuid(), saved.getRecipientAccountOrUpi(), saved.getTriggerReason())
        );

        // Notify Ganesh's Backend API so Ganesh can update policy state & prep mobile TTS
        try {
            log.info("Notifying Ganesh Backend API at {} regarding settled payout {}", ganeshBaseUrl, txnId);
            restClient.post()
                    .uri(ganeshBaseUrl + "/api/v1/integration/anosh/payout")
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            log.warn("Could not notify Ganesh's Backend directly (it will sync on next poll): {}", e.getMessage());
        }

        return new PayoutExecutionResponse(
                true,
                saved.getTransactionId(),
                saved.getPolicyUuid(),
                saved.getFarmerUuid(),
                saved.getAmount(),
                saved.getStatus(),
                saved.getRecipientAccountOrUpi(),
                "Payout settled automatically via Agricultural Insurance Reserve: Rs. " + saved.getAmount() + " credited to " + recipient
        );
    }

    public List<PayoutTransaction> getAllTransactions() {
        return payoutRepository.findAllByOrderByTimestampDesc();
    }

    public List<PayoutTransaction> getTransactionsForFarmer(String farmerUuid) {
        return payoutRepository.findByFarmerUuid(farmerUuid);
    }
}
