package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.FarmerDTO;
import com.coderabbits.fs2604.model.Farmer;
import com.coderabbits.fs2604.repository.FarmerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final AuditService auditService;

    public FarmerService(FarmerRepository farmerRepository, AuditService auditService) {
        this.farmerRepository = farmerRepository;
        this.auditService = auditService;
    }

    @Transactional
    public Farmer registerFarmer(FarmerDTO dto) {
        String uuid = (dto.getFarmerUuid() != null && !dto.getFarmerUuid().isBlank()) 
                ? dto.getFarmerUuid() 
                : UUID.randomUUID().toString();

        // Idempotency check: if farmer already registered with same UUID
        Optional<Farmer> existingByUuid = farmerRepository.findByFarmerUuid(uuid);
        if (existingByUuid.isPresent()) {
            return existingByUuid.get();
        }

        // Check if existing by Aadhaar
        Optional<Farmer> existingByAadhaar = farmerRepository.findByAadhaarNumber(dto.getAadhaarNumber());
        if (existingByAadhaar.isPresent()) {
            return existingByAadhaar.get();
        }

        Farmer farmer = new Farmer(
                uuid,
                dto.getAadhaarNumber(),
                dto.getName(),
                dto.getPhone(),
                dto.getVillage(),
                dto.getDistrict(),
                dto.getState(),
                dto.getPreferredLanguage(),
                dto.getBankAccountNumber(),
                dto.getUpiId()
        );

        Farmer saved = farmerRepository.save(farmer);

        auditService.log(
                "FARMER_REGISTERED",
                "GANESH_API",
                "FARMER",
                saved.getFarmerUuid(),
                String.format("Farmer %s registered from %s, %s", saved.getName(), saved.getDistrict(), saved.getState())
        );

        return saved;
    }

    public Optional<Farmer> getByUuid(String uuid) {
        return farmerRepository.findByFarmerUuid(uuid);
    }

    public Optional<Farmer> getByPhone(String phone) {
        return farmerRepository.findByPhone(phone);
    }

    public List<Farmer> getByDistrict(String district) {
        return farmerRepository.findByDistrictIgnoreCase(district);
    }

    public List<Farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }
}
