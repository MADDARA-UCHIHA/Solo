package com.aicallblocker.app.service;

/**
 * Foydalanuvchi boshlab bergan audio uchun on-device STT adapteri.
 * Android qurilmasida offline til paketi bo'lsa, tarmoqsiz ham ishlaydi.
 * PSTN qo'ng'iroq audiosini o'zboshimchalik bilan yozib olmaydi.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\u0018\u0000 &2\u00020\u0001:\u0001&B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\fJ\u0006\u0010\u000f\u001a\u00020\u0007J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0006H\u0002J\b\u0010\u0012\u001a\u00020\u0007H\u0016J\u0012\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0007H\u0016J\u0010\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0018\u001a\u00020\nH\u0016J\u001a\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0012\u0010\u001d\u001a\u00020\u00072\b\u0010\u001e\u001a\u0004\u0018\u00010\u001cH\u0016J\u0012\u0010\u001f\u001a\u00020\u00072\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J\u0012\u0010 \u001a\u00020\u00072\b\u0010\u001e\u001a\u0004\u0018\u00010\u001cH\u0016J\u0010\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020#H\u0016J\u0006\u0010$\u001a\u00020\u0007J\u0006\u0010%\u001a\u00020\u0007R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/aicallblocker/app/service/LocalSpeechRecognizer;", "Landroid/speech/RecognitionListener;", "context", "Landroid/content/Context;", "onPartial", "Lkotlin/Function1;", "", "", "onFinal", "errorCallback", "", "stateCallback", "(Landroid/content/Context;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "recognizer", "Landroid/speech/SpeechRecognizer;", "destroy", "emitState", "message", "onBeginningOfSpeech", "onBufferReceived", "buffer", "", "onEndOfSpeech", "onError", "error", "onEvent", "eventType", "params", "Landroid/os/Bundle;", "onPartialResults", "results", "onReadyForSpeech", "onResults", "onRmsChanged", "rmsdB", "", "start", "stop", "Companion", "app_debug"})
public final class LocalSpeechRecognizer implements android.speech.RecognitionListener {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> onPartial = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> onFinal = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.Unit> errorCallback = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<java.lang.String, kotlin.Unit> stateCallback = null;
    @org.jetbrains.annotations.Nullable()
    private final android.speech.SpeechRecognizer recognizer = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "LocalSpeechRecognizer";
    @org.jetbrains.annotations.NotNull()
    public static final com.aicallblocker.app.service.LocalSpeechRecognizer.Companion Companion = null;
    
    public LocalSpeechRecognizer(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPartial, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFinal, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> errorCallback, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> stateCallback) {
        super();
    }
    
    public final void start() {
    }
    
    public final void stop() {
    }
    
    public final void destroy() {
    }
    
    @java.lang.Override()
    public void onPartialResults(@org.jetbrains.annotations.Nullable()
    android.os.Bundle results) {
    }
    
    @java.lang.Override()
    public void onResults(@org.jetbrains.annotations.Nullable()
    android.os.Bundle results) {
    }
    
    @java.lang.Override()
    public void onError(int error) {
    }
    
    @java.lang.Override()
    public void onReadyForSpeech(@org.jetbrains.annotations.Nullable()
    android.os.Bundle params) {
    }
    
    @java.lang.Override()
    public void onBeginningOfSpeech() {
    }
    
    @java.lang.Override()
    public void onRmsChanged(float rmsdB) {
    }
    
    @java.lang.Override()
    public void onBufferReceived(@org.jetbrains.annotations.Nullable()
    byte[] buffer) {
    }
    
    @java.lang.Override()
    public void onEndOfSpeech() {
    }
    
    @java.lang.Override()
    public void onEvent(int eventType, @org.jetbrains.annotations.Nullable()
    android.os.Bundle params) {
    }
    
    private final void emitState(java.lang.String message) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/aicallblocker/app/service/LocalSpeechRecognizer$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}