package com.aicallblocker.app.service;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\rR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/aicallblocker/app/service/CallDashboardStateStore;", "", "()V", "current", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/aicallblocker/app/service/CallDashboardState;", "getCurrent", "()Lkotlinx/coroutines/flow/StateFlow;", "state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "update", "", "transform", "Lkotlin/Function1;", "app_debug"})
public final class CallDashboardStateStore {
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.MutableStateFlow<com.aicallblocker.app.service.CallDashboardState> state = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlinx.coroutines.flow.StateFlow<com.aicallblocker.app.service.CallDashboardState> current = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.aicallblocker.app.service.CallDashboardStateStore INSTANCE = null;
    
    private CallDashboardStateStore() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.aicallblocker.app.service.CallDashboardState> getCurrent() {
        return null;
    }
    
    public final void update(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.aicallblocker.app.service.CallDashboardState, com.aicallblocker.app.service.CallDashboardState> transform) {
    }
}