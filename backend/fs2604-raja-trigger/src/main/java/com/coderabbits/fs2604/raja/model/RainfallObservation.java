package com.coderabbits.fs2604.raja.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RainfallObservation {
    private String observationId;
    private String district;
    private String state;
    private String stationId;
    private LocalDate observationDate;
    private Double rainfallMm;
    private String source; // "IMD_AUTOMATIC_WEATHER_STATION", "CHIRPS_SATELLITE_GRID"
    private LocalDateTime ingestedAt;

    public RainfallObservation() {
        this.ingestedAt = LocalDateTime.now();
    }

    public RainfallObservation(String observationId, String district, String state, 
                               String stationId, LocalDate observationDate, 
                               Double rainfallMm, String source) {
        this.observationId = observationId;
        this.district = district;
        this.state = state;
        this.stationId = stationId;
        this.observationDate = observationDate;
        this.rainfallMm = rainfallMm;
        this.source = source;
        this.ingestedAt = LocalDateTime.now();
    }

    public String getObservationId() {
        return observationId;
    }

    public void setObservationId(String observationId) {
        this.observationId = observationId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDate getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDate observationDate) {
        this.observationDate = observationDate;
    }

    public Double getRainfallMm() {
        return rainfallMm;
    }

    public void setRainfallMm(Double rainfallMm) {
        this.rainfallMm = rainfallMm;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getIngestedAt() {
        return ingestedAt;
    }

    public void setIngestedAt(LocalDateTime ingestedAt) {
        this.ingestedAt = ingestedAt;
    }
}
