package com.coderabbits.fs2604.anosh.controller;

import com.coderabbits.fs2604.anosh.dto.PayoutExecutionRequest;
import com.coderabbits.fs2604.anosh.dto.PayoutExecutionResponse;
import com.coderabbits.fs2604.anosh.model.PayoutTransaction;
import com.coderabbits.fs2604.anosh.service.PayoutProcessingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payouts")
@Tag(name = "Anosh - Payout Processing", description = "Parametric Claim Settlement and Reserve Disbursements")
public class PayoutController {

    private final PayoutProcessingService payoutService;

    public PayoutController(PayoutProcessingService payoutService) {
        this.payoutService = payoutService;
    }

    @PostMapping("/execute")
    @Operation(summary = "Execute payout disbursement", description = "Called by Raja's trigger engine or Ganesh's backend when a parametric condition is met")
    public ResponseEntity<PayoutExecutionResponse> executePayout(@Valid @RequestBody PayoutExecutionRequest request) {
        PayoutExecutionResponse response = payoutService.processPayout(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "List all payout transactions", description = "Fetches all historical parametric payouts")
    public ResponseEntity<List<PayoutTransaction>> listPayouts() {
        return ResponseEntity.ok(payoutService.getAllTransactions());
    }

    @GetMapping("/farmer/{farmerUuid}")
    @Operation(summary = "List payouts for farmer", description = "Fetches all payouts disbursed to a specific farmer")
    public ResponseEntity<List<PayoutTransaction>> listPayoutsForFarmer(@PathVariable String farmerUuid) {
        return ResponseEntity.ok(payoutService.getTransactionsForFarmer(farmerUuid));
    }
}
