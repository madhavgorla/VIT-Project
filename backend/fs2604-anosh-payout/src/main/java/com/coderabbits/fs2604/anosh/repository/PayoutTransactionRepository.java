package com.coderabbits.fs2604.anosh.repository;

import com.coderabbits.fs2604.anosh.model.PayoutTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayoutTransactionRepository extends JpaRepository<PayoutTransaction, Long> {
    Optional<PayoutTransaction> findByTransactionId(String transactionId);
    List<PayoutTransaction> findByFarmerUuid(String farmerUuid);
    List<PayoutTransaction> findByPolicyUuid(String policyUuid);
    List<PayoutTransaction> findAllByOrderByTimestampDesc();
}
