package com.coderabbits.fs2604.anosh.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payout_transactions")
public class PayoutTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "policy_uuid", nullable = false)
    private String policyUuid;

    @Column(name = "farmer_uuid", nullable = false)
    private String farmerUuid;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "recipient_account_or_upi")
    private String recipientAccountOrUpi;

    @Column(name = "trigger_reason")
    private String triggerReason;

    @Column(name = "status", nullable = false)
    private String status = "SETTLED"; // SETTLED, PROCESSING, FAILED

    @Column(name = "settlement_bank")
    private String settlementBank = "NATIONAL_AGRICULTURAL_INSURANCE_POOL";

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public PayoutTransaction() {
    }

    public PayoutTransaction(String transactionId, String policyUuid, String farmerUuid, 
                             Double amount, String recipientAccountOrUpi, String triggerReason) {
        this.transactionId = transactionId;
        this.policyUuid = policyUuid;
        this.farmerUuid = farmerUuid;
        this.amount = amount;
        this.recipientAccountOrUpi = recipientAccountOrUpi;
        this.triggerReason = triggerReason;
        this.status = "SETTLED";
        this.settlementBank = "NATIONAL_AGRICULTURAL_INSURANCE_POOL";
        this.timestamp = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPolicyUuid() {
        return policyUuid;
    }

    public void setPolicyUuid(String policyUuid) {
        this.policyUuid = policyUuid;
    }

    public String getFarmerUuid() {
        return farmerUuid;
    }

    public void setFarmerUuid(String farmerUuid) {
        this.farmerUuid = farmerUuid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRecipientAccountOrUpi() {
        return recipientAccountOrUpi;
    }

    public void setRecipientAccountOrUpi(String recipientAccountOrUpi) {
        this.recipientAccountOrUpi = recipientAccountOrUpi;
    }

    public String getTriggerReason() {
        return triggerReason;
    }

    public void setTriggerReason(String triggerReason) {
        this.triggerReason = triggerReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSettlementBank() {
        return settlementBank;
    }

    public void setSettlementBank(String settlementBank) {
        this.settlementBank = settlementBank;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
