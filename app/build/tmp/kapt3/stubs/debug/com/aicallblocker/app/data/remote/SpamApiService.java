package com.aicallblocker.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/aicallblocker/app/data/remote/SpamApiService;", "", "checkNumber", "Lcom/aicallblocker/app/data/remote/SpamCheckResponse;", "number", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reportNumber", "", "request", "Lcom/aicallblocker/app/data/remote/SpamReportRequest;", "(Lcom/aicallblocker/app/data/remote/SpamReportRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface SpamApiService {
    
    @retrofit2.http.GET(value = "v1/spam/check")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object checkNumber(@retrofit2.http.Query(value = "number")
    @org.jetbrains.annotations.NotNull()
    java.lang.String number, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.aicallblocker.app.data.remote.SpamCheckResponse> $completion);
    
    @retrofit2.http.POST(value = "v1/spam/report")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object reportNumber(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.aicallblocker.app.data.remote.SpamReportRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}