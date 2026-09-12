package com.coderabbits.fs2604.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SyncBatchRequest {
    private String deviceId;
    private String farmerUuid; // optional: if sync is scoped to one logged-in farmer
    private LocalDateTime clientTimestamp;
    private List<FarmerDTO> offlineFarmers = new ArrayList<>();
    private List<PolicyDTO> offlinePolicies = new ArrayList<>();

    public SyncBatchRequest() {
        this.clientTimestamp = LocalDateTime.now();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFarmerUuid() {
        return farmerUuid;
    }

    public void setFarmerUuid(String farmerUuid) {
        this.farmerUuid = farmerUuid;
    }

    public LocalDateTime getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(LocalDateTime clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    public List<FarmerDTO> getOfflineFarmers() {
        return offlineFarmers;
    }

    public void setOfflineFarmers(List<FarmerDTO> offlineFarmers) {
        this.offlineFarmers = offlineFarmers;
    }

    public List<PolicyDTO> getOfflinePolicies() {
        return offlinePolicies;
    }

    public void setOfflinePolicies(List<PolicyDTO> offlinePolicies) {
        this.offlinePolicies = offlinePolicies;
    }
}
