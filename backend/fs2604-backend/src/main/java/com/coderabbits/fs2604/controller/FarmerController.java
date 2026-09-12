package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.FarmerDTO;
import com.coderabbits.fs2604.model.Farmer;
import com.coderabbits.fs2604.service.FarmerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/farmers")
@Tag(name = "1. Farmer Management (Ganesh)", description = "Farmer onboarding, profiles, and regional language preferences")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping
    @Operation(summary = "Register a new farmer", description = "Onboards a farmer into the parametric insurance system")
    public ResponseEntity<Farmer> registerFarmer(@Valid @RequestBody FarmerDTO dto) {
        Farmer created = farmerService.registerFarmer(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get farmer by UUID", description = "Retrieves farmer details using client or server UUID")
    public ResponseEntity<Farmer> getFarmerByUuid(@PathVariable String uuid) {
        return farmerService.getByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phone}")
    @Operation(summary = "Get farmer by phone number", description = "Searches for farmer by mobile phone number")
    public ResponseEntity<Farmer> getFarmerByPhone(@PathVariable String phone) {
        return farmerService.getByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List or filter farmers", description = "Returns all registered farmers or filters by district")
    public ResponseEntity<List<Farmer>> listFarmers(@RequestParam(required = false) String district) {
        if (district != null && !district.isBlank()) {
            return ResponseEntity.ok(farmerService.getByDistrict(district));
        }
        return ResponseEntity.ok(farmerService.getAllFarmers());
    }
}
