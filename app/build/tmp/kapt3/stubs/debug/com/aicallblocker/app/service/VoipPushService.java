package com.aicallblocker.app.service;

/**
 * Backend (masalan Twilio webhook orqali AI suhbatni yakunlagach) shu
 * ilovaga FCM push yuboradi. Bu — audio emas, faqat matn/JSON payload:
 * chaqiruvchi raqam, transkript, AI xulosasi, spam skori.
 *
 * Ilova hech qachon xom audio bilan ishlamaydi — bu arxitekturaning
 * asosiy afzalligi: Android tomonida hech qanday maxfiylik/OEM cheklovi
 * yo'q, chunki og'ir audio ishi to'liq serverda (WebRTC/SIP + AI) bo'ladi.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/aicallblocker/app/service/VoipPushService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "onMessageReceived", "", "message", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "", "app_debug"})
public final class VoipPushService extends com.google.firebase.messaging.FirebaseMessagingService {
    
    public VoipPushService() {
        super();
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage message) {
    }
}