package com.coderabbits.fs2604.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "farmers")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farmer_uuid", unique = true, nullable = false)
    private String farmerUuid;

    @Column(name = "aadhaar_number", nullable = false)
    private String aadhaarNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "village")
    private String village;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "preferred_language")
    private String preferredLanguage = "te"; // "te" (Telugu), "hi" (Hindi), "en" (English)

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Farmer() {
    }

    public Farmer(String farmerUuid, String aadhaarNumber, String name, String phone, 
                  String village, String district, String state, String preferredLanguage, 
                  String bankAccountNumber, String upiId) {
        this.farmerUuid = farmerUuid;
        this.aadhaarNumber = aadhaarNumber;
        this.name = name;
        this.phone = phone;
        this.village = village;
        this.district = district;
        this.state = state;
        this.preferredLanguage = (preferredLanguage != null && !preferredLanguage.isBlank()) ? preferredLanguage : "te";
        this.bankAccountNumber = bankAccountNumber;
        this.upiId = upiId;
        this.createdAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFarmerUuid() {
        return farmerUuid;
    }

    public void setFarmerUuid(String farmerUuid) {
        this.farmerUuid = farmerUuid;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
