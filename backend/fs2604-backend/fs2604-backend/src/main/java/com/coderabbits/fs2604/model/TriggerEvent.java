package com.coderabbits.fs2604.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trigger_events")
public class TriggerEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_uuid", unique = true, nullable = false)
    private String eventUuid;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "reading_date", nullable = false)
    private LocalDate readingDate;

    @Column(name = "measured_rainfall_mm", nullable = false)
    private Double measuredRainfallMm;

    @Column(name = "threshold_rainfall_mm", nullable = false)
    private Double thresholdRainfallMm;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_operator", nullable = false)
    private TriggerOperator triggerOperator;

    @Column(name = "triggered", nullable = false)
    private boolean triggered;

    @Column(name = "source", nullable = false)
    private String source; // "IMD", "CHIRPS", or "SIMULATION"

    @Column(name = "affected_policies_count")
    private Integer affectedPoliciesCount = 0;

    @Column(name = "notes")
    private String notes;

    @Column(name = "evaluated_at", nullable = false)
    private LocalDateTime evaluatedAt;

    public TriggerEvent() {
    }

    public TriggerEvent(String eventUuid, String district, LocalDate readingDate, 
                        Double measuredRainfallMm, Double thresholdRainfallMm, 
                        TriggerOperator triggerOperator, boolean triggered, 
                        String source, Integer affectedPoliciesCount, String notes) {
        this.eventUuid = eventUuid;
        this.district = district;
        this.readingDate = readingDate;
        this.measuredRainfallMm = measuredRainfallMm;
        this.thresholdRainfallMm = thresholdRainfallMm;
        this.triggerOperator = triggerOperator;
        this.triggered = triggered;
        this.source = source;
        this.affectedPoliciesCount = affectedPoliciesCount;
        this.notes = notes;
        this.evaluatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.evaluatedAt == null) {
            this.evaluatedAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventUuid() {
        return eventUuid;
    }

    public void setEventUuid(String eventUuid) {
        this.eventUuid = eventUuid;
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

    public Integer getAffectedPoliciesCount() {
        return affectedPoliciesCount;
    }

    public void setAffectedPoliciesCount(Integer affectedPoliciesCount) {
        this.affectedPoliciesCount = affectedPoliciesCount;
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
