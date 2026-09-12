package com.coderabbits.fs2604.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_uuid", unique = true, nullable = false)
    private String policyUuid;

    @Column(name = "policy_number", unique = true, nullable = false)
    private String policyNumber;

    @Column(name = "farmer_uuid", nullable = false)
    private String farmerUuid;

    @Column(name = "crop_type", nullable = false)
    private String cropType;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "season", nullable = false)
    private String season;

    @Column(name = "coverage_amount", nullable = false)
    private Double coverageAmount;

    @Column(name = "premium_amount", nullable = false)
    private Double premiumAmount;

    @Column(name = "threshold_rainfall_mm", nullable = false)
    private Double thresholdRainfallMm;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_operator", nullable = false)
    private TriggerOperator triggerOperator = TriggerOperator.LESS_THAN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PolicyStatus status = PolicyStatus.ACTIVE;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Policy() {
    }

    public Policy(String policyUuid, String policyNumber, String farmerUuid, String cropType,
                  String district, String season, Double coverageAmount, Double premiumAmount,
                  Double thresholdRainfallMm, TriggerOperator triggerOperator,
                  LocalDate startDate, LocalDate endDate) {
        this.policyUuid = policyUuid;
        this.policyNumber = policyNumber;
        this.farmerUuid = farmerUuid;
        this.cropType = cropType;
        this.district = district;
        this.season = season;
        this.coverageAmount = coverageAmount;
        this.premiumAmount = premiumAmount;
        this.thresholdRainfallMm = thresholdRainfallMm;
        this.triggerOperator = (triggerOperator != null) ? triggerOperator : TriggerOperator.LESS_THAN;
        this.status = PolicyStatus.ACTIVE;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
