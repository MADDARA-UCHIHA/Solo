package com.aicallblocker.app.service

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CallDashboardState(
    val activeNumber: String? = null,
    val status: String = "Tayyor",
    val transcript: String = ""
)

object CallDashboardStateStore {
    private val state = MutableStateFlow(CallDashboardState())
    val current: StateFlow<CallDashboardState> = state.asStateFlow()

    fun update(transform: (CallDashboardState) -> CallDashboardState) {
        state.value = transform(state.value)
    }
}
