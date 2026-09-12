package com.coderabbits.fs2604.anosh.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PayoutExecutionRequest {

    @Schema(description = "Policy UUID", example = "pol-groundnut-anantapur-001")
    private String policyUuid = "pol-groundnut-anantapur-001";

    @Schema(description = "Farmer UUID", example = "f1-anantapur-uuid-001")
    private String farmerUuid = "f1-anantapur-uuid-001";

    @Schema(description = "Payout amount in Rupees (Default: 10000)", example = "10000.0")
    private Double amount = 10000.0;

    @Schema(description = "Farmer Bank Account or UPI ID", example = "ramesh.naidu@ybl")
    private String recipientAccountOrUpi = "ramesh.naidu@ybl";

    @Schema(description = "Reason for parametric trigger / trouble", example = "Rainfall deficit: Emergency parametric compensation of Rs. 10,000")
    private String triggerReason = "Rainfall deficit: Emergency parametric compensation of Rs. 10,000";

    public PayoutExecutionRequest() {
    }

    public PayoutExecutionRequest(String policyUuid, String farmerUuid, Double amount, String recipientAccountOrUpi, String triggerReason) {
        this.policyUuid = (policyUuid != null && !policyUuid.isBlank() && !policyUuid.equals("string")) ? policyUuid : "pol-groundnut-anantapur-001";
        this.farmerUuid = (farmerUuid != null && !farmerUuid.isBlank() && !farmerUuid.equals("string")) ? farmerUuid : "f1-anantapur-uuid-001";
        this.amount = (amount != null && amount > 0) ? amount : 10000.0;
        this.recipientAccountOrUpi = (recipientAccountOrUpi != null && !recipientAccountOrUpi.isBlank() && !recipientAccountOrUpi.equals("string")) ? recipientAccountOrUpi : "ramesh.naidu@ybl";
        this.triggerReason = (triggerReason != null && !triggerReason.isBlank() && !triggerReason.equals("string")) ? triggerReason : "Rainfall trouble: Rs. 10000 emergency relief";
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
        return (amount != null && amount > 0) ? amount : 10000.0;
    }

    public void setAmount(Double amount) {
        this.amount = (amount != null && amount > 0) ? amount : 10000.0;
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
}
