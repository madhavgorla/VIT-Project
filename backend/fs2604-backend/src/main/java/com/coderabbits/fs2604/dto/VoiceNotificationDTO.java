package com.coderabbits.fs2604.dto;

import java.time.LocalDateTime;

public class VoiceNotificationDTO {
    private String notificationUuid;
    private String farmerUuid;
    private String languageCode; // "te" (Telugu), "hi" (Hindi), "en" (English)
    private String title;
    private String spokenMessage; // Phonetic/clear string to be fed directly to Flutter TTS
    private String eventType; // "POLICY_CONFIRMED", "PARAMETRIC_TRIGGER_ACTIVATED", "PAYOUT_CREDITED"
    private LocalDateTime timestamp;

    public VoiceNotificationDTO() {
        this.timestamp = LocalDateTime.now();
    }

    public VoiceNotificationDTO(String notificationUuid, String farmerUuid, String languageCode, 
                                String title, String spokenMessage, String eventType) {
        this.notificationUuid = notificationUuid;
        this.farmerUuid = farmerUuid;
        this.languageCode = languageCode;
        this.title = title;
        this.spokenMessage = spokenMessage;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    public String getNotificationUuid() {
        return notificationUuid;
    }

    public void setNotificationUuid(String notificationUuid) {
        this.notificationUuid = notificationUuid;
    }

    public String getFarmerUuid() {
        return farmerUuid;
    }

    public void setFarmerUuid(String farmerUuid) {
        this.farmerUuid = farmerUuid;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpokenMessage() {
        return spokenMessage;
    }

    public void setSpokenMessage(String spokenMessage) {
        this.spokenMessage = spokenMessage;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
