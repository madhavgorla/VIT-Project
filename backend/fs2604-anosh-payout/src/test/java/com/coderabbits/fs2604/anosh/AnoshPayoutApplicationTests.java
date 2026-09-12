package com.coderabbits.fs2604.anosh;

import com.coderabbits.fs2604.anosh.dto.PayoutExecutionRequest;
import com.coderabbits.fs2604.anosh.dto.PayoutExecutionResponse;
import com.coderabbits.fs2604.anosh.service.PayoutProcessingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnoshPayoutApplicationTests {

    @Autowired
    private PayoutProcessingService payoutService;

    @Test
    void testPayoutProcessingAndAudit() {
        PayoutExecutionRequest request = new PayoutExecutionRequest(
                "pol-test-uuid-999",
                "farmer-test-uuid-999",
                15000.0,
                "ramesh@upi",
                "Drought deficit triggered"
        );

        PayoutExecutionResponse response = payoutService.processPayout(request);

        assertTrue(response.isSuccess());
        assertNotNull(response.getTransactionId());
        assertEquals("SETTLED", response.getStatus());
        assertEquals(15000.0, response.getAmount());
    }
}
