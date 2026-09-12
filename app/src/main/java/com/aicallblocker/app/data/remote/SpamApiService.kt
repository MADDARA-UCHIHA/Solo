package com.aicallblocker.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class SpamCheckResponse(
    val phoneNumber: String,
    val isSpam: Boolean,
    val spamScore: Float,
    val reportsCount: Int
)

data class SpamReportRequest(
    val phoneNumber: String,
    val reason: String = "user_report"
)

interface SpamApiService {
    @GET("v1/spam/check")
    suspend fun checkNumber(@Query("number") number: String): SpamCheckResponse

    @retrofit2.http.POST("v1/spam/report")
    suspend fun reportNumber(@retrofit2.http.Body request: SpamReportRequest)
}
