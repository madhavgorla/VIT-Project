package com.coderabbits.fs2604.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PayoutRequestDTO {

    @NotBlank(message = "Policy UUID is required")
    private String policyUuid;

    @NotBlank(message = "Farmer UUID is required")
    private String farmerUuid;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private String triggerReason;
    private String recipientAccountOrUpi;

    public PayoutRequestDTO() {
    }

    public PayoutRequestDTO(String policyUuid, String farmerUuid, Double amount, String triggerReason, String recipientAccountOrUpi) {
        this.policyUuid = policyUuid;
        this.farmerUuid = farmerUuid;
        this.amount = amount;
        this.triggerReason = triggerReason;
        this.recipientAccountOrUpi = recipientAccountOrUpi;
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

    public String getTriggerReason() {
        return triggerReason;
    }

    public void setTriggerReason(String triggerReason) {
        this.triggerReason = triggerReason;
    }

    public String getRecipientAccountOrUpi() {
        return recipientAccountOrUpi;
    }

    public void setRecipientAccountOrUpi(String recipientAccountOrUpi) {
        this.recipientAccountOrUpi = recipientAccountOrUpi;
    }
}
