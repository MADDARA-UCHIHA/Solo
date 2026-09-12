package com.aicallblocker.app.service;

/**
 * Qurilmada ishlaydigan, tarmoq talab qilmaydigan bazaviy analizator.
 * Bu model LLM o'rnini bosmaydi, lekin maxfiylikni saqlagan holda tezkor
 * signal beradi va keyinchalik MediaPipe/Gemini Nano adapteri bilan almashtirilishi mumkin.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R \u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00040\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/aicallblocker/app/service/CallTranscriptAnalyzer;", "", "()V", "TAG", "", "contextPatterns", "", "Lkotlin/Pair;", "Lkotlin/text/Regex;", "fraudPatterns", "sensitiveRequestKeywords", "threatKeywords", "analyze", "Lcom/aicallblocker/app/service/TranscriptAnalysis;", "transcript", "analyzeSafely", "app_debug"})
public final class CallTranscriptAnalyzer {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> fraudPatterns = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<kotlin.Pair<kotlin.text.Regex, java.lang.String>> contextPatterns = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> threatKeywords = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.String> sensitiveRequestKeywords = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CallTranscriptAnalyzer";
    @org.jetbrains.annotations.NotNull()
    public static final com.aicallblocker.app.service.CallTranscriptAnalyzer INSTANCE = null;
    
    private CallTranscriptAnalyzer() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.aicallblocker.app.service.TranscriptAnalysis analyze(@org.jetbrains.annotations.NotNull()
    java.lang.String transcript) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.aicallblocker.app.service.TranscriptAnalysis analyzeSafely(@org.jetbrains.annotations.NotNull()
    java.lang.String transcript) {
        return null;
    }
}