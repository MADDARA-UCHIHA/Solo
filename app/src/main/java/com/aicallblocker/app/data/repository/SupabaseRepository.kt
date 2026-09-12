package com.aicallblocker.app.data.repository
import io.github.jan-tennert.supabase.gotrue.auth
import io.github.jan-tennert.supabase.gotrue.user.UserInfo

import com.aicallblocker.app.data.remote.SupabaseNumberOffer
import com.aicallblocker.app.data.remote.SupabaseSdkClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.serialization.Serializable

@Serializable
data class NumberOrderInsert(
    val offer_id: String? = null,
    val requested_tier: String
)

class SupabaseRepository {
    private val client = SupabaseSdkClient.client

    suspend fun listAvailableOffers(): List<SupabaseNumberOffer> =
        client.from("virtual_number_offers")
            .select {
                filter { eq("status", "available") }
                order("tier", Order.ASCENDING)
            }
            .decodeList()

    suspend fun createNumberOrder(tier: String, offerId: String? = null) {
        check(client.auth.currentUserOrNull() != null) {
            "Sign in is required before ordering a virtual number."
        }
        client.from("number_orders").insert(
            NumberOrderInsert(offer_id = offerId, requested_tier = tier)
        )
    }
}
