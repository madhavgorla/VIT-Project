package com.coderabbits.fs2604.dto;

import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.Policy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SyncBatchResponse {
    private boolean success;
    private String message;
    private LocalDateTime serverTimestamp;
    private List<String> acknowledgedFarmerUuids = new ArrayList<>();
    private List<String> acknowledgedPolicyUuids = new ArrayList<>();
    private List<Policy> updatedPolicies = new ArrayList<>();
    private List<PayoutRecord> payouts = new ArrayList<>();
    private List<VoiceNotificationDTO> voiceNotifications = new ArrayList<>();

    public SyncBatchResponse() {
        this.serverTimestamp = LocalDateTime.now();
        this.success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(LocalDateTime serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public List<String> getAcknowledgedFarmerUuids() {
        return acknowledgedFarmerUuids;
    }

    public void setAcknowledgedFarmerUuids(List<String> acknowledgedFarmerUuids) {
        this.acknowledgedFarmerUuids = acknowledgedFarmerUuids;
    }

    public List<String> getAcknowledgedPolicyUuids() {
        return acknowledgedPolicyUuids;
    }

    public void setAcknowledgedPolicyUuids(List<String> acknowledgedPolicyUuids) {
        this.acknowledgedPolicyUuids = acknowledgedPolicyUuids;
    }

    public List<Policy> getUpdatedPolicies() {
        return updatedPolicies;
    }

    public void setUpdatedPolicies(List<Policy> updatedPolicies) {
        this.updatedPolicies = updatedPolicies;
    }

    public List<PayoutRecord> getPayouts() {
        return payouts;
    }

    public void setPayouts(List<PayoutRecord> payouts) {
        this.payouts = payouts;
    }

    public List<VoiceNotificationDTO> getVoiceNotifications() {
        return voiceNotifications;
    }

    public void setVoiceNotifications(List<VoiceNotificationDTO> voiceNotifications) {
        this.voiceNotifications = voiceNotifications;
    }
}
