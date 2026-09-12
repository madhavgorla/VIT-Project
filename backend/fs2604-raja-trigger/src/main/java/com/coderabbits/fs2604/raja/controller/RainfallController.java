package com.coderabbits.fs2604.raja.controller;

import com.coderabbits.fs2604.raja.dto.RainfallIngestionRequest;
import com.coderabbits.fs2604.raja.model.RainfallObservation;
import com.coderabbits.fs2604.raja.service.ImdChirpsIngestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rainfall")
@Tag(name = "Raja - Rainfall Ingestion", description = "IMD Weather Stations & CHIRPS Satellite Data Feeds")
public class RainfallController {

    private final ImdChirpsIngestionService ingestionService;

    public RainfallController(ImdChirpsIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    @Operation(summary = "Ingest rainfall measurement", description = "Feeds rainfall data from IMD AWS or CHIRPS satellite grid")
    public ResponseEntity<RainfallObservation> ingest(@Valid @RequestBody RainfallIngestionRequest request) {
        RainfallObservation observation = ingestionService.recordObservation(request);
        return ResponseEntity.ok(observation);
    }

    @GetMapping("/observations")
    @Operation(summary = "List all district observations", description = "Returns active observations across monitored districts")
    public ResponseEntity<List<RainfallObservation>> listObservations() {
        return ResponseEntity.ok(ingestionService.getAllObservations());
    }

    @GetMapping("/district/{district}")
    @Operation(summary = "Get observation for district", description = "Returns latest rainfall reading for district")
    public ResponseEntity<RainfallObservation> getDistrictObservation(@PathVariable String district) {
        return ingestionService.getLatestObservation(district)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
