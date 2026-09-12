package com.coderabbits.fs2604.raja.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RainfallIngestionRequest {

    @NotBlank(message = "District is required")
    private String district;

    private String state;
    private String stationId;
    private LocalDate observationDate;

    @NotNull(message = "Rainfall mm is required")
    private Double rainfallMm;

    private String source; // "IMD" or "CHIRPS"

    public RainfallIngestionRequest() {
        this.observationDate = LocalDate.now();
        this.source = "IMD";
    }

    public RainfallIngestionRequest(String district, String state, String stationId, 
                                    LocalDate observationDate, Double rainfallMm, String source) {
        this.district = district;
        this.state = state;
        this.stationId = stationId;
        this.observationDate = (observationDate != null) ? observationDate : LocalDate.now();
        this.rainfallMm = rainfallMm;
        this.source = (source != null) ? source : "IMD";
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
}
