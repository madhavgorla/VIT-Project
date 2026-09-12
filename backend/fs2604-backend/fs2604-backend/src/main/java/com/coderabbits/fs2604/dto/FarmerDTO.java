package com.coderabbits.fs2604.dto;

import jakarta.validation.constraints.NotBlank;

public class FarmerDTO {
    private String farmerUuid;

    @NotBlank(message = "Aadhaar number is required")
    private String aadhaarNumber;

    @NotBlank(message = "Farmer name is required")
    private String name;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String village;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String preferredLanguage = "te"; // "te" (Telugu), "hi" (Hindi), "en" (English)
    private String bankAccountNumber;
    private String upiId;

    public FarmerDTO() {
    }

    public FarmerDTO(String farmerUuid, String aadhaarNumber, String name, String phone, 
                     String village, String district, String state, String preferredLanguage, 
                     String bankAccountNumber, String upiId) {
        this.farmerUuid = farmerUuid;
        this.aadhaarNumber = aadhaarNumber;
        this.name = name;
        this.phone = phone;
        this.village = village;
        this.district = district;
        this.state = state;
        this.preferredLanguage = preferredLanguage;
        this.bankAccountNumber = bankAccountNumber;
        this.upiId = upiId;
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
}
