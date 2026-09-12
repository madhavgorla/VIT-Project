package com.coderabbits.fs2604.raja.dto;

import java.util.List;
import java.util.Map;

public class DistrictThresholdDTO {
    private String district;
    private int activePoliciesCount;
    private Double averageThresholdMm;
    private List<Map<String, Object>> policies;

    public DistrictThresholdDTO() {
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

    public List<Map<String, Object>> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Map<String, Object>> policies) {
        this.policies = policies;
    }
}
