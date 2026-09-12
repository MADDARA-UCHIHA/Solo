package com.aicallblocker.app.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\r"}, d2 = {"Lcom/aicallblocker/app/data/remote/VoipBackendApi;", "", "getCallSessions", "", "Lcom/aicallblocker/app/data/remote/CallSessionSummary;", "userId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerForwarding", "", "request", "Lcom/aicallblocker/app/data/remote/RegisterForwardingRequest;", "(Lcom/aicallblocker/app/data/remote/RegisterForwardingRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface VoipBackendApi {
    
    /**
     * Foydalanuvchini backend'ga ro'yxatdan o'tkazish: qaysi virtual raqam
     * unga tegishli va push xabarlarni qayerga yuborish kerak.
     */
    @retrofit2.http.POST(value = "v1/forwarding/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerForwarding(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.aicallblocker.app.data.remote.RegisterForwardingRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * O'tgan AI-boshqargan qo'ng'iroqlar tarixini olish (agar push o'tkazib
     * yuborilgan bo'lsa yoki UI ro'yxat ko'rsatishi kerak bo'lsa).
     */
    @retrofit2.http.GET(value = "v1/sessions/{userId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCallSessions(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.aicallblocker.app.data.remote.CallSessionSummary>> $completion);
}