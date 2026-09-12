package com.coderabbits.fs2604.dto;

import com.coderabbits.fs2604.model.Policy;
import java.util.List;

public class PolicyThresholdResponseDTO {
    private String district;
    private int activePoliciesCount;
    private Double averageThresholdMm;
    private List<Policy> policies;

    public PolicyThresholdResponseDTO() {
    }

    public PolicyThresholdResponseDTO(String district, int activePoliciesCount, Double averageThresholdMm, List<Policy> policies) {
        this.district = district;
        this.activePoliciesCount = activePoliciesCount;
        this.averageThresholdMm = averageThresholdMm;
        this.policies = policies;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getActivePoliciesCount() {
        return activePoliciesCount;
    }

    public void setActivePoliciesCount(int activePoliciesCount) {
        this.activePoliciesCount = activePoliciesCount;
    }

    public Double getAverageThresholdMm() {
        return averageThresholdMm;
    }

    public void setAverageThresholdMm(Double averageThresholdMm) {
        this.averageThresholdMm = averageThresholdMm;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }
}
