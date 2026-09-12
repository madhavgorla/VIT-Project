package com.coderabbits.fs2604.raja.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TriggerDecisionResult {
    private String decisionId;
    private String district;
    private LocalDate readingDate;
    private Double measuredRainfallMm;
    private Double thresholdRainfallMm;
    private String triggerOperator; // "LESS_THAN", "GREATER_THAN"
    private boolean triggered;
    private String source;
    private String notes;
    private LocalDateTime evaluatedAt;

    public TriggerDecisionResult() {
        this.evaluatedAt = LocalDateTime.now();
    }

    public TriggerDecisionResult(String decisionId, String district, LocalDate readingDate, 
                                 Double measuredRainfallMm, Double thresholdRainfallMm, 
                                 String triggerOperator, boolean triggered, 
                                 String source, String notes) {
        this.decisionId = decisionId;
        this.district = district;
        this.readingDate = readingDate;
        this.measuredRainfallMm = measuredRainfallMm;
        this.thresholdRainfallMm = thresholdRainfallMm;
        this.triggerOperator = triggerOperator;
        this.triggered = triggered;
        this.source = source;
        this.notes = notes;
        this.evaluatedAt = LocalDateTime.now();
    }

    public String getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(LocalDate readingDate) {
        this.readingDate = readingDate;
    }

    public Double getMeasuredRainfallMm() {
        return measuredRainfallMm;
    }

    public void setMeasuredRainfallMm(Double measuredRainfallMm) {
        this.measuredRainfallMm = measuredRainfallMm;
    }

    public Double getThresholdRainfallMm() {
        return thresholdRainfallMm;
    }

    public void setThresholdRainfallMm(Double thresholdRainfallMm) {
        this.thresholdRainfallMm = thresholdRainfallMm;
    }

    public String getTriggerOperator() {
        return triggerOperator;
    }

    public void setTriggerOperator(String triggerOperator) {
        this.triggerOperator = triggerOperator;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public void setTriggered(boolean triggered) {
        this.triggered = triggered;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(LocalDateTime evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }
}
