package com.aicallblocker.app.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\fH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\"\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\fH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u0012\u0010\u0010\u001a\u00020\f2\b\u0010\u0011\u001a\u0004\u0018\u00010\fH\u0002J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0017\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0012\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u001b0!J&\u0010#\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010$\u001a\u00020\f2\u0006\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010\'R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/aicallblocker/app/data/repository/CallRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dao", "Lcom/aicallblocker/app/data/local/NumberDao;", "records", "Lcom/aicallblocker/app/data/local/CallRecordDao;", "addToBlacklist", "", "number", "", "label", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addToWhitelist", "csv", "value", "evaluateLocalOnly", "Lcom/aicallblocker/app/domain/model/CallVerdict;", "rawNumber", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "evaluateRemote", "exportBackup", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportCsv", "getReasonStats", "", "Lcom/aicallblocker/app/data/local/ReasonCount;", "importBackup", "json", "normalize", "observeBlockedCalls", "Lkotlinx/coroutines/flow/Flow;", "Lcom/aicallblocker/app/data/local/CallRecordEntity;", "recordBlockedCall", "reason", "spamScore", "", "(Ljava/lang/String;Ljava/lang/String;FLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class CallRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.aicallblocker.app.data.local.NumberDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.aicallblocker.app.data.local.CallRecordDao records = null;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.aicallblocker.app.data.repository.CallRepository INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.aicallblocker.app.data.repository.CallRepository.Companion Companion = null;
    
    public CallRepository(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * FAQAT mahalliy Room bazasidan tekshiradi. Tarmoqqa chiqmaydi.
     * CallScreeningService.onScreenCall() ichida timeout bilan chaqiriladi —
     * chunki tizim bu metodga juda qisqa vaqt beradi va tarmoq so'rovi
     * deadline'ni oshirib yuborishi mumkin.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object evaluateLocalOnly(@org.jetbrains.annotations.NotNull()
    java.lang.String rawNumber, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.aicallblocker.app.domain.model.CallVerdict> $completion) {
        return null;
    }
    
    /**
     * Cloud API orqali global spam bazasini tekshiradi (tarmoqqa chiqadi,
     * sekin bo'lishi mumkin). Screening deadline'idan TASHQARIDA, fonda
     * chaqirilishi kerak — hech qachon onScreenCall() ichida to'g'ridan-to'g'ri emas.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object evaluateRemote(@org.jetbrains.annotations.NotNull()
    java.lang.String rawNumber, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.aicallblocker.app.domain.model.CallVerdict> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToBlacklist(@org.jetbrains.annotations.NotNull()
    java.lang.String number, @org.jetbrains.annotations.Nullable()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToWhitelist(@org.jetbrains.annotations.NotNull()
    java.lang.String number, @org.jetbrains.annotations.Nullable()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.aicallblocker.app.data.local.CallRecordEntity>> observeBlockedCalls() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordBlockedCall(@org.jetbrains.annotations.NotNull()
    java.lang.String number, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, float spamScore, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getReasonStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.aicallblocker.app.data.local.ReasonCount>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportBackup(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object exportCsv(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object importBackup(@org.jetbrains.annotations.NotNull()
    java.lang.String json, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String csv(java.lang.String value) {
        return null;
    }
    
    private final java.lang.String normalize(java.lang.String number) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/aicallblocker/app/data/repository/CallRepository$Companion;", "", "()V", "INSTANCE", "Lcom/aicallblocker/app/data/repository/CallRepository;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.aicallblocker.app.data.repository.CallRepository getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}