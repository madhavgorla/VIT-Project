package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.PolicyDTO;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/policies")
@Tag(name = "2. Policy Management (Ganesh)", description = "Parametric micro-insurance policy issuance and queries")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PostMapping
    @Operation(summary = "Issue a micro-insurance policy", description = "Creates a parametric policy linked to district rainfall thresholds")
    public ResponseEntity<Policy> createPolicy(@Valid @RequestBody PolicyDTO dto) {
        Policy created = policyService.createPolicy(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get policy by UUID", description = "Retrieves policy by its unique identifier")
    public ResponseEntity<Policy> getPolicyByUuid(@PathVariable String uuid) {
        return policyService.getByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/farmer/{farmerUuid}")
    @Operation(summary = "Get policies for a farmer", description = "Lists all policies purchased by a specific farmer")
    public ResponseEntity<List<Policy>> getPoliciesForFarmer(@PathVariable String farmerUuid) {
        return ResponseEntity.ok(policyService.getByFarmerUuid(farmerUuid));
    }

    @GetMapping("/district/{district}")
    @Operation(summary = "Get active policies in a district", description = "Retrieves active policies currently under parametric monitoring")
    public ResponseEntity<List<Policy>> getActivePoliciesInDistrict(@PathVariable String district) {
        return ResponseEntity.ok(policyService.getActivePoliciesInDistrict(district));
    }

    @GetMapping
    @Operation(summary = "List all policies", description = "Returns all policies across all states and districts")
    public ResponseEntity<List<Policy>> listAllPolicies() {
        return ResponseEntity.ok(policyService.getAllPolicies());
    }
}
