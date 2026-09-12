package com.coderabbits.fs2604.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payout_records")
public class PayoutRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payout_uuid", unique = true, nullable = false)
    private String payoutUuid;

    @Column(name = "policy_uuid", nullable = false)
    private String policyUuid;

    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @Column(name = "farmer_uuid", nullable = false)
    private String farmerUuid;

    @Column(name = "farmer_name", nullable = false)
    private String farmerName;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PayoutStatus status = PayoutStatus.INITIATED;

    @Column(name = "transaction_reference", unique = true)
    private String transactionReference;

    @Column(name = "recipient_account_or_upi")
    private String recipientAccountOrUpi;

    @Column(name = "trigger_reason")
    private String triggerReason;

    @Column(name = "synced_to_mobile")
    private boolean syncedToMobile = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    public PayoutRecord() {
    }

    public PayoutRecord(String payoutUuid, String policyUuid, String policyNumber, 
                        String farmerUuid, String farmerName, Double amount, 
                        String recipientAccountOrUpi, String triggerReason) {
        this.payoutUuid = payoutUuid;
        this.policyUuid = policyUuid;
        this.policyNumber = policyNumber;
        this.farmerUuid = farmerUuid;
        this.farmerName = farmerName;
        this.amount = amount;
        this.recipientAccountOrUpi = recipientAccountOrUpi;
        this.triggerReason = triggerReason;
        this.status = PayoutStatus.SUCCESS; // Auto-processed in parametric insurance
        this.transactionReference = "TXN-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 9000 + 1000);
        this.createdAt = LocalDateTime.now();
        this.processedAt = LocalDateTime.now();
        this.syncedToMobile = false;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.processedAt == null && this.status == PayoutStatus.SUCCESS) {
            this.processedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayoutUuid() {
        return payoutUuid;
    }

    public void setPayoutUuid(String payoutUuid) {
        this.payoutUuid = payoutUuid;
    }

    public String getPolicyUuid() {
        return policyUuid;
    }

    public void setPolicyUuid(String policyUuid) {
        this.policyUuid = policyUuid;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getFarmerUuid() {
        return farmerUuid;
    }

    public void setFarmerUuid(String farmerUuid) {
        this.farmerUuid = farmerUuid;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PayoutStatus getStatus() {
        return status;
    }

    public void setStatus(PayoutStatus status) {
        this.status = status;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
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

    public boolean isSyncedToMobile() {
        return syncedToMobile;
    }

    public void setSyncedToMobile(boolean syncedToMobile) {
        this.syncedToMobile = syncedToMobile;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
