package com.coderabbits.fs2604.raja.service;

import com.coderabbits.fs2604.raja.dto.RainfallIngestionRequest;
import com.coderabbits.fs2604.raja.model.RainfallObservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImdChirpsIngestionService {

    private final Map<String, RainfallObservation> observations = new ConcurrentHashMap<>();

    public ImdChirpsIngestionService() {
        // Seed initial observation for key agricultural districts
        recordObservation(new RainfallIngestionRequest("Anantapur", "Andhra Pradesh", "IMD-AWS-ANTP-01", LocalDate.now(), 14.5, "IMD_AUTOMATIC_WEATHER_STATION"));
        recordObservation(new RainfallIngestionRequest("Kurnool", "Andhra Pradesh", "IMD-AWS-KRNL-02", LocalDate.now(), 38.0, "IMD_AUTOMATIC_WEATHER_STATION"));
        recordObservation(new RainfallIngestionRequest("Solapur", "Maharashtra", "CHIRPS-GRID-SLPR-03", LocalDate.now(), 18.0, "CHIRPS_SATELLITE_GRID"));
    }

    public RainfallObservation recordObservation(RainfallIngestionRequest request) {
        String id = "OBS-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 900 + 100);
        RainfallObservation obs = new RainfallObservation(
                id,
                request.getDistrict(),
                request.getState() != null ? request.getState() : "Andhra Pradesh",
                request.getStationId() != null ? request.getStationId() : "AWS-" + request.getDistrict().toUpperCase(),
                request.getObservationDate() != null ? request.getObservationDate() : LocalDate.now(),
                request.getRainfallMm(),
                request.getSource() != null ? request.getSource() : "IMD"
        );
        observations.put(obs.getDistrict().toLowerCase(), obs);
        return obs;
    }

    public Optional<RainfallObservation> getLatestObservation(String district) {
        return Optional.ofNullable(observations.get(district.toLowerCase()));
    }

    public List<RainfallObservation> getAllObservations() {
        return new ArrayList<>(observations.values());
    }
}
