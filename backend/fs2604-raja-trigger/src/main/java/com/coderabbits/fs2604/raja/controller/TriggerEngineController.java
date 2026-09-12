package com.coderabbits.fs2604.raja.controller;

import com.coderabbits.fs2604.raja.model.TriggerDecisionResult;
import com.coderabbits.fs2604.raja.service.TriggerEvaluationEngine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/triggers")
@Tag(name = "Raja - Trigger Engine", description = "Parametric threshold comparison and automatic trigger execution")
public class TriggerEngineController {

    private final TriggerEvaluationEngine triggerEngine;

    public TriggerEngineController(TriggerEvaluationEngine triggerEngine) {
        this.triggerEngine = triggerEngine;
    }

    @PostMapping("/evaluate")
    @Operation(summary = "Evaluate district rainfall against thresholds", 
               description = "Queries Ganesh (8080) for policy thresholds, compares rainfall, and when triggered dispatches payouts to Anosh (8082)")
    public ResponseEntity<TriggerDecisionResult> evaluate(
            @RequestParam String district,
            @RequestParam(required = false) Double rainfallMm) {
        TriggerDecisionResult result = triggerEngine.evaluateDistrict(district, rainfallMm);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    @Operation(summary = "List trigger decision history", description = "Fetches history of all evaluated rainfall triggers")
    public ResponseEntity<List<TriggerDecisionResult>> getHistory() {
        return ResponseEntity.ok(triggerEngine.getHistory());
    }
}
