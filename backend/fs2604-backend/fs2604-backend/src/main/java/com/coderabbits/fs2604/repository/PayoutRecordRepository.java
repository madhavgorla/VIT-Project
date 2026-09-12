package com.coderabbits.fs2604.repository;

import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.PayoutStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutRecordRepository extends JpaRepository<PayoutRecord, Long> {
    Optional<PayoutRecord> findByPayoutUuid(String payoutUuid);
    List<PayoutRecord> findByFarmerUuid(String farmerUuid);
    List<PayoutRecord> findByPolicyUuid(String policyUuid);
    List<PayoutRecord> findByStatus(PayoutStatus status);
    List<PayoutRecord> findByFarmerUuidAndSyncedToMobileFalse(String farmerUuid);
    List<PayoutRecord> findBySyncedToMobileFalse();
}
