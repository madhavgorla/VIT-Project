package com.coderabbits.fs2604.controller;

import com.coderabbits.fs2604.dto.SyncBatchRequest;
import com.coderabbits.fs2604.dto.SyncBatchResponse;
import com.coderabbits.fs2604.service.OfflineSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sync")
@Tag(name = "3. Offline Sync & TTS (Madhav ↔ Ganesh)", description = "Offline-first batch synchronization and Text-to-Speech generation for mobile app")
public class OfflineSyncController {

    private final OfflineSyncService offlineSyncService;

    public OfflineSyncController(OfflineSyncService offlineSyncService) {
        this.offlineSyncService = offlineSyncService;
    }

    @PostMapping
    @Operation(summary = "Synchronize offline mobile data", 
               description = "Accepts batched offline registrations, returns acknowledgements, latest policy statuses, payouts, and localized TTS speech text")
    public ResponseEntity<SyncBatchResponse> sync(@RequestBody SyncBatchRequest request) {
        SyncBatchResponse response = offlineSyncService.processSyncBatch(request);
        return ResponseEntity.ok(response);
    }
}
