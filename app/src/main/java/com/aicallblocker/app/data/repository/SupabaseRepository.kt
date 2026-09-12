package com.aicallblocker.app.data.repository

import com.aicallblocker.app.data.remote.SupabaseSdkClient
import io.github.jan-tennert.supabase.gotrue.auth
import io.github.jan-tennert.supabase.gotrue.user.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseRepository @Inject constructor(
    private val supabaseSdkClient: SupabaseSdkClient
) {
    val client = supabaseSdkClient.client

    suspend fun getCurrentUser(): UserInfo? {
        return try {
            client.auth.currentUserOrNull()
        } catch (e: Exception) {
            null
        }
    }
}