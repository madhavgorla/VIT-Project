package com.coderabbits.fs2604.dto;

import com.coderabbits.fs2604.model.PolicyStatus;
import com.coderabbits.fs2604.model.TriggerOperator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class PolicyDTO {
    private String policyUuid;
    private String policyNumber;

    @NotBlank(message = "Farmer UUID is required")
    private String farmerUuid;

    @NotBlank(message = "Crop type is required")
    private String cropType;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "Season is required")
    private String season;

    @NotNull(message = "Coverage amount is required")
    @Positive(message = "Coverage amount must be positive")
    private Double coverageAmount;

    @NotNull(message = "Premium amount is required")
    @Positive(message = "Premium amount must be positive")
    private Double premiumAmount;

    @NotNull(message = "Threshold rainfall mm is required")
    private Double thresholdRainfallMm;

    private TriggerOperator triggerOperator = TriggerOperator.LESS_THAN;
    private PolicyStatus status = PolicyStatus.ACTIVE;
    private LocalDate startDate;
    private LocalDate endDate;

    public PolicyDTO() {
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

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public Double getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(Double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Double getThresholdRainfallMm() {
        return thresholdRainfallMm;
    }

    public void setThresholdRainfallMm(Double thresholdRainfallMm) {
        this.thresholdRainfallMm = thresholdRainfallMm;
    }

    public TriggerOperator getTriggerOperator() {
        return triggerOperator;
    }

    public void setTriggerOperator(TriggerOperator triggerOperator) {
        this.triggerOperator = triggerOperator;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
