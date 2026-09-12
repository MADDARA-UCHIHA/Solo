package com.aicallblocker.app.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SupabaseNumberOffer(
    val id: String,
    val e164_number: String,
    val formatted_number: String,
    val country_code: String,
    val tier: String,
    val price_minor: Long,
    val currency: String,
    val status: String
)
