package com.coderabbits.fs2604.raja;

import com.coderabbits.fs2604.raja.dto.RainfallIngestionRequest;
import com.coderabbits.fs2604.raja.model.RainfallObservation;
import com.coderabbits.fs2604.raja.model.TriggerDecisionResult;
import com.coderabbits.fs2604.raja.service.ImdChirpsIngestionService;
import com.coderabbits.fs2604.raja.service.TriggerEvaluationEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RajaTriggerApplicationTests {

    @Autowired
    private ImdChirpsIngestionService ingestionService;

    @Autowired
    private TriggerEvaluationEngine triggerEngine;

    @Test
    void testRainfallIngestionAndTriggerEvaluation() {
        // Ingest reading of 12.0 mm (drought) in Anantapur
        RainfallIngestionRequest request = new RainfallIngestionRequest(
                "Anantapur",
                "Andhra Pradesh",
                "TEST-STATION-01",
                null,
                12.0,
                "IMD_AWS"
        );
        RainfallObservation obs = ingestionService.recordObservation(request);
        assertEquals(12.0, obs.getRainfallMm());

        // Evaluate district trigger
        TriggerDecisionResult decision = triggerEngine.evaluateDistrict("Anantapur", 12.0);
        assertNotNull(decision);
        assertTrue(decision.isTriggered());
        assertEquals("Anantapur", decision.getDistrict());
    }
}
