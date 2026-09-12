package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.PolicyDTO;
import com.coderabbits.fs2604.dto.PolicyThresholdResponseDTO;
import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.model.PolicyStatus;
import com.coderabbits.fs2604.model.TriggerOperator;
import com.coderabbits.fs2604.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final AuditService auditService;

    public PolicyService(PolicyRepository policyRepository, AuditService auditService) {
        this.policyRepository = policyRepository;
        this.auditService = auditService;
    }

    @Transactional
    public Policy createPolicy(PolicyDTO dto) {
        String uuid = (dto.getPolicyUuid() != null && !dto.getPolicyUuid().isBlank())
                ? dto.getPolicyUuid()
                : UUID.randomUUID().toString();

        Optional<Policy> existing = policyRepository.findByPolicyUuid(uuid);
        if (existing.isPresent()) {
            return existing.get();
        }

        String policyNumber = (dto.getPolicyNumber() != null && !dto.getPolicyNumber().isBlank())
                ? dto.getPolicyNumber()
                : generatePolicyNumber(dto.getDistrict());

        LocalDate startDate = (dto.getStartDate() != null) ? dto.getStartDate() : LocalDate.now();
        LocalDate endDate = (dto.getEndDate() != null) ? dto.getEndDate() : startDate.plusMonths(4); // Typical crop cycle

        Policy policy = new Policy(
                uuid,
                policyNumber,
                dto.getFarmerUuid(),
                dto.getCropType(),
                dto.getDistrict(),
                dto.getSeason(),
                dto.getCoverageAmount(),
                dto.getPremiumAmount(),
                dto.getThresholdRainfallMm(),
                (dto.getTriggerOperator() != null) ? dto.getTriggerOperator() : TriggerOperator.LESS_THAN,
                startDate,
                endDate
        );

        Policy saved = policyRepository.save(policy);

        auditService.log(
                "POLICY_CREATED",
                "GANESH_API",
                "POLICY",
                saved.getPolicyUuid(),
                String.format("Policy %s created for crop %s, coverage Rs. %.0f, threshold %.1f mm",
                        saved.getPolicyNumber(), saved.getCropType(), saved.getCoverageAmount(), saved.getThresholdRainfallMm())
        );

        return saved;
    }

    public Optional<Policy> getByUuid(String uuid) {
        return policyRepository.findByPolicyUuid(uuid);
    }

    public List<Policy> getByFarmerUuid(String farmerUuid) {
        return policyRepository.findByFarmerUuid(farmerUuid);
    }

    public List<Policy> getActivePoliciesInDistrict(String district) {
        return policyRepository.findByDistrictIgnoreCaseAndStatus(district, PolicyStatus.ACTIVE);
    }

    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    public PolicyThresholdResponseDTO getThresholdSummaryForDistrict(String district) {
        List<Policy> activePolicies = getActivePoliciesInDistrict(district);
        double avgThreshold = activePolicies.stream()
                .mapToDouble(Policy::getThresholdRainfallMm)
                .average()
                .orElse(0.0);

        return new PolicyThresholdResponseDTO(
                district,
                activePolicies.size(),
                avgThreshold,
                activePolicies
        );
    }

    @Transactional
    public Policy updatePolicyStatus(String policyUuid, PolicyStatus status) {
        Policy policy = policyRepository.findByPolicyUuid(policyUuid)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found with UUID: " + policyUuid));
        policy.setStatus(status);
        Policy updated = policyRepository.save(policy);

        auditService.log(
                "POLICY_STATUS_CHANGED",
                "GANESH_API",
                "POLICY",
                updated.getPolicyUuid(),
                "Status updated to " + status
        );

        return updated;
    }

    private String generatePolicyNumber(String district) {
        String cleanDistrict = district.toUpperCase().replaceAll("[^A-Z]", "");
        String prefix = cleanDistrict.length() >= 3 ? cleanDistrict.substring(0, 3) : "IND";
        return String.format("POL-%s-%d-%04d", prefix, LocalDate.now().getYear(), (int)(Math.random() * 9000 + 1000));
    }
}
