package com.coderabbits.fs2604.repository;

import com.coderabbits.fs2604.model.Policy;
import com.coderabbits.fs2604.model.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findByPolicyUuid(String policyUuid);
    Optional<Policy> findByPolicyNumber(String policyNumber);
    List<Policy> findByFarmerUuid(String farmerUuid);
    List<Policy> findByDistrictIgnoreCaseAndStatus(String district, PolicyStatus status);
    List<Policy> findByStatus(PolicyStatus status);
    List<Policy> findByDistrictIgnoreCase(String district);
    boolean existsByPolicyUuid(String policyUuid);
}
