package com.coderabbits.fs2604.service;

import com.coderabbits.fs2604.dto.VoiceNotificationDTO;
import com.coderabbits.fs2604.model.Farmer;
import com.coderabbits.fs2604.model.PayoutRecord;
import com.coderabbits.fs2604.model.Policy;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TextToSpeechFormatterService {

    /**
     * Generates voice message when an offline policy is synced and confirmed.
     */
    public VoiceNotificationDTO createPolicyConfirmationVoice(Farmer farmer, Policy policy) {
        String lang = (farmer.getPreferredLanguage() != null) ? farmer.getPreferredLanguage().toLowerCase() : "te";
        String title;
        String spokenText;

        switch (lang) {
            case "te": // Telugu
                title = "బీమా పాలసీ ఆమోదించబడింది";
                spokenText = String.format("నమస్కారం %s గారు, మీ %s పంట బీమా పాలసీ %s విజయవంతంగా సింక్ అయ్యింది. మీ పంట రక్షణలో ఉంది.",
                        farmer.getName(), policy.getCropType(), policy.getPolicyNumber());
                break;
            case "hi": // Hindi
                title = "फसल बीमा पॉलिसी स्वीकृत";
                spokenText = String.format("नमस्ते %s जी, आपकी %s फसल बीमा पॉलिसी %s सफलतापूर्वक सक्रिय हो गई है।",
                        farmer.getName(), policy.getCropType(), policy.getPolicyNumber());
                break;
            default: // English
                title = "Crop Insurance Policy Activated";
                spokenText = String.format("Hello %s, your %s crop insurance policy %s is confirmed and active.",
                        farmer.getName(), policy.getCropType(), policy.getPolicyNumber());
                break;
        }

        return new VoiceNotificationDTO(
                UUID.randomUUID().toString(),
                farmer.getFarmerUuid(),
                lang,
                title,
                spokenText,
                "POLICY_CONFIRMED"
        );
    }

    /**
     * Generates voice message when a parametric trigger fires and payout is disbursed.
     */
    public VoiceNotificationDTO createPayoutVoice(Farmer farmer, PayoutRecord payout, Double measuredRainfallMm, Double thresholdRainfallMm) {
        String lang = (farmer.getPreferredLanguage() != null) ? farmer.getPreferredLanguage().toLowerCase() : "te";
        String title;
        String spokenText;

        switch (lang) {
            case "te": // Telugu
                title = "పరిహారం జమ చేయబడింది (Parametric Payout)";
                spokenText = String.format("ముఖ్య సమాచారం %s గారు! మీ జిల్లా %s లో వర్షపాతం లోటు %.1f మి.మీ. నమోదైంది. మీ పాలసీ %s ద్వారా రూపాయలు %.0f మీ బ్యాంకు ఖాతాకు జమ చేయబడ్డాయి. లావాదేవీ సంఖ్య: %s.",
                        farmer.getName(), farmer.getDistrict(), measuredRainfallMm, payout.getPolicyNumber(), payout.getAmount(), payout.getTransactionReference());
                break;
            case "hi": // Hindi
                title = "बीमा क्लेम राशि जमा (Parametric Payout)";
                spokenText = String.format("सूचना %s जी! आपके जिले %s में कम बारिश (%.1f मिमी) दर्ज हुई है। स्वचालित क्लेम के तहत रु. %.0f आपके खाते में भेज दिए गए हैं। संदर्भ संख्या: %s.",
                        farmer.getName(), farmer.getDistrict(), measuredRainfallMm, payout.getAmount(), payout.getTransactionReference());
                break;
            default: // English
                title = "Parametric Insurance Payout Credited";
                spokenText = String.format("Alert %s! Low rainfall of %.1f mm recorded in %s. As per your parametric micro-insurance, an automatic payout of Rupees %.0f has been credited. Ref: %s.",
                        farmer.getName(), measuredRainfallMm, farmer.getDistrict(), payout.getAmount(), payout.getTransactionReference());
                break;
        }

        return new VoiceNotificationDTO(
                UUID.randomUUID().toString(),
                farmer.getFarmerUuid(),
                lang,
                title,
                spokenText,
                "PAYOUT_CREDITED"
        );
    }

    /**
     * Generates voice message specifically for emergency rainfall trouble relief (Rs. 10,000).
     */
    public VoiceNotificationDTO createRainfallTroubleReliefVoice(Farmer farmer, Double amount, String transactionRef) {
        String lang = (farmer.getPreferredLanguage() != null) ? farmer.getPreferredLanguage().toLowerCase() : "te";
        String title;
        String spokenText;

        switch (lang) {
            case "te": // Telugu
                title = "వర్షపాత నష్ట అత్యవసర సహాయం (Rs. 10,000)";
                spokenText = String.format("ముఖ్య సమాచారం %s గారు! మీ ప్రాంతంలో వర్షపాత లోటు కారణంగా నష్టపరిహారంగా రూపాయలు %.0f నేరుగా మీ ఖాతాకు బదిలీ చేయబడ్డాయి. లావాదేవీ సంఖ్య: %s.",
                        farmer.getName(), amount, transactionRef);
                break;
            case "hi": // Hindi
                title = "वर्षा संकट राहत राशि (रु. 10,000)";
                spokenText = String.format("महत्वपूर्ण सूचना %s जी! आपके क्षेत्र में वर्षा की कमी के कारण आपातकालीन राहत राशि रु. %.0f आपके बैंक खाते में जमा कर दी गई है। संदर्भ संख्या: %s.",
                        farmer.getName(), amount, transactionRef);
                break;
            default: // English
                title = "Rainfall Trouble Emergency Relief (Rs. 10,000)";
                spokenText = String.format("Urgent Alert %s! Due to rainfall deficit trouble, emergency disaster relief of Rupees %.0f has been credited to your account. Transaction Ref: %s.",
                        farmer.getName(), amount, transactionRef);
                break;
        }

        return new VoiceNotificationDTO(
                UUID.randomUUID().toString(),
                farmer.getFarmerUuid(),
                lang,
                title,
                spokenText,
                "RAINFALL_RELIEF_CREDITED"
        );
    }
}
