package com.coderabbits.fs2604.anosh.dto;

import java.time.LocalDateTime;

public class PayoutExecutionResponse {
    private boolean success;
    private String transactionId;
    private String policyUuid;
    private String farmerUuid;
    private Double amount;
    private String status;
    private String recipientAccountOrUpi;
    private String message;
    private LocalDateTime timestamp;

    public PayoutExecutionResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public PayoutExecutionResponse(boolean success, String transactionId, String policyUuid, 
                                   String farmerUuid, Double amount, String status, 
                                   String recipientAccountOrUpi, String message) {
        this.success = success;
        this.transactionId = transactionId;
        this.policyUuid = policyUuid;
        this.farmerUuid = farmerUuid;
        this.amount = amount;
        this.status = status;
        this.recipientAccountOrUpi = recipientAccountOrUpi;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecipientAccountOrUpi() {
        return recipientAccountOrUpi;
    }

    public void setRecipientAccountOrUpi(String recipientAccountOrUpi) {
        this.recipientAccountOrUpi = recipientAccountOrUpi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
