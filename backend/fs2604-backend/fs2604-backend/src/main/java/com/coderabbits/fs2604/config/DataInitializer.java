package com.coderabbits.fs2604.config;

import com.coderabbits.fs2604.dto.FarmerDTO;
import com.coderabbits.fs2604.dto.PolicyDTO;
import com.coderabbits.fs2604.model.TriggerOperator;
import com.coderabbits.fs2604.service.FarmerService;
import com.coderabbits.fs2604.service.PolicyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final FarmerService farmerService;
    private final PolicyService policyService;

    public DataInitializer(FarmerService farmerService, PolicyService policyService) {
        this.farmerService = farmerService;
        this.policyService = policyService;
    }

    @Override
    public void run(String... args) {
        log.info("--- Seeding FS-2604 Hackathon Initial Data (Team Code Rabbits) ---");

        // Farmer 1: Ramesh Naidu (Anantapur, AP - Telugu)
        FarmerDTO f1 = new FarmerDTO(
                "f1-anantapur-uuid-001",
                "912345678901",
                "Ramesh Naidu",
                "9876543210",
                "Bukkarayasamudram",
                "Anantapur",
                "Andhra Pradesh",
                "te", // Telugu
                "SBIN00012345678",
                "ramesh.naidu@ybl"
        );
        var savedF1 = farmerService.registerFarmer(f1);

        // Farmer 2: Lakshmi Devi (Anantapur, AP - Telugu)
        FarmerDTO f2 = new FarmerDTO(
                "f2-anantapur-uuid-002",
                "923456789012",
                "Lakshmi Devi",
                "9876543211",
                "Kalyandurg",
                "Anantapur",
                "Andhra Pradesh",
                "te", // Telugu
                "ANDB00087654321",
                "lakshmidevi@okhdfcbank"
        );
        var savedF2 = farmerService.registerFarmer(f2);

        // Farmer 3: Suresh Patil (Solapur, Maharashtra - Hindi)
        FarmerDTO f3 = new FarmerDTO(
                "f3-solapur-uuid-003",
                "934567890123",
                "Suresh Patil",
                "9876543212",
                "Pandharpur",
                "Solapur",
                "Maharashtra",
                "hi", // Hindi
                "MAHB00054321098",
                "sureshpatil@sbi"
        );
        var savedF3 = farmerService.registerFarmer(f3);

        // Policy 1: Ramesh - Groundnut in Anantapur (Parametric Threshold: Rainfall < 45.0 mm)
        PolicyDTO p1 = new PolicyDTO();
        p1.setPolicyUuid("pol-groundnut-anantapur-001");
        p1.setPolicyNumber("POL-ANTP-2026-001");
        p1.setFarmerUuid(savedF1.getFarmerUuid());
        p1.setCropType("Groundnut");
        p1.setDistrict("Anantapur");
        p1.setSeason("Kharif 2026");
        p1.setCoverageAmount(25000.0);
        p1.setPremiumAmount(750.0);
        p1.setThresholdRainfallMm(45.0);
        p1.setTriggerOperator(TriggerOperator.LESS_THAN);
        p1.setStartDate(LocalDate.now().minusMonths(1));
        p1.setEndDate(LocalDate.now().plusMonths(3));
        policyService.createPolicy(p1);

        // Policy 2: Lakshmi - Cotton in Anantapur (Parametric Threshold: Rainfall < 50.0 mm)
        PolicyDTO p2 = new PolicyDTO();
        p2.setPolicyUuid("pol-cotton-anantapur-002");
        p2.setPolicyNumber("POL-ANTP-2026-002");
        p2.setFarmerUuid(savedF2.getFarmerUuid());
        p2.setCropType("Cotton");
        p2.setDistrict("Anantapur");
        p2.setSeason("Kharif 2026");
        p2.setCoverageAmount(30000.0);
        p2.setPremiumAmount(900.0);
        p2.setThresholdRainfallMm(50.0);
        p2.setTriggerOperator(TriggerOperator.LESS_THAN);
        p2.setStartDate(LocalDate.now().minusMonths(1));
        p2.setEndDate(LocalDate.now().plusMonths(3));
        policyService.createPolicy(p2);

        // Policy 3: Suresh - Millets in Solapur (Parametric Threshold: Rainfall < 40.0 mm)
        PolicyDTO p3 = new PolicyDTO();
        p3.setPolicyUuid("pol-millets-solapur-003");
        p3.setPolicyNumber("POL-SOLP-2026-003");
        p3.setFarmerUuid(savedF3.getFarmerUuid());
        p3.setCropType("Millets");
        p3.setDistrict("Solapur");
        p3.setSeason("Kharif 2026");
        p3.setCoverageAmount(18000.0);
        p3.setPremiumAmount(540.0);
        p3.setThresholdRainfallMm(40.0);
        p3.setTriggerOperator(TriggerOperator.LESS_THAN);
        p3.setStartDate(LocalDate.now().minusMonths(1));
        p3.setEndDate(LocalDate.now().plusMonths(3));
        policyService.createPolicy(p3);

        log.info("--- FS-2604 Demo Data Successfully Initialized ---");
    }
}
