package com.aicallblocker.app.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00060\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0003H\'\u00a8\u0006\u0011"}, d2 = {"Lcom/aicallblocker/app/data/local/CallRecordDao;", "", "count", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "countByReason", "", "Lcom/aicallblocker/app/data/local/ReasonCount;", "getAll", "Lcom/aicallblocker/app/data/local/CallRecordEntity;", "insert", "", "record", "(Lcom/aicallblocker/app/data/local/CallRecordEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeRecent", "Lkotlinx/coroutines/flow/Flow;", "limit", "app_debug"})
@androidx.room.Dao()
public abstract interface CallRecordDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.aicallblocker.app.data.local.CallRecordEntity record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM call_records ORDER BY blockedAt DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.aicallblocker.app.data.local.CallRecordEntity>> observeRecent(int limit);
    
    @androidx.room.Query(value = "SELECT * FROM call_records ORDER BY blockedAt ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.aicallblocker.app.data.local.CallRecordEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM call_records")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object count(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT reason, COUNT(*) AS total FROM call_records GROUP BY reason ORDER BY total DESC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object countByReason(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.aicallblocker.app.data.local.ReasonCount>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}