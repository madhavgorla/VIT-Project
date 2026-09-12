package com.coderabbits.fs2604.repository;

import com.coderabbits.fs2604.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByFarmerUuid(String farmerUuid);
    Optional<Farmer> findByPhone(String phone);
    Optional<Farmer> findByAadhaarNumber(String aadhaarNumber);
    List<Farmer> findByDistrictIgnoreCase(String district);
    boolean existsByFarmerUuid(String farmerUuid);
    boolean existsByPhone(String phone);
    boolean existsByAadhaarNumber(String aadhaarNumber);
}
