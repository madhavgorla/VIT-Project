package com.coderabbits.fs2604.dto;

import com.coderabbits.fs2604.model.TriggerOperator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TriggerDecisionDTO {

    @NotBlank(message = "District is required")
    private String district;

    @NotNull(message = "Reading date is required")
    private LocalDate readingDate;

    @NotNull(message = "Measured rainfall mm is required")
    private Double measuredRainfallMm;

    private Double thresholdRainfallMm;
    private TriggerOperator triggerOperator = TriggerOperator.LESS_THAN;
    private boolean triggered = false;
    private String source = "IMD"; // IMD or CHIRPS
    private String notes;

    public TriggerDecisionDTO() {
        this.readingDate = LocalDate.now();
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

    public TriggerOperator getTriggerOperator() {
        return triggerOperator;
    }

    public void setTriggerOperator(TriggerOperator triggerOperator) {
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
}
