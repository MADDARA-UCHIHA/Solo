# 1. SupabaseSdkClient.kt faylini toza holda qaytadan yozamiz
@"
package com.aicallblocker.app.data.remote

import com.aicallblocker.app.BuildConfig
import io.github.jan-tennert.supabase.createSupabaseClient
import io.github.jan-tennert.supabase.gotrue.Auth
import io.github.jan-tennert.supabase.gotrue.auth
import io.github.jan-tennert.supabase.postgrest.Postgrest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseSdkClient @Inject constructor() {
    val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY
    ) {
        install(Auth)
        install(Postgrest)
    }
}
"@ | Out-File -Encoding utf8NoBOM app/src/main/java/com/aicallblocker/app/data/remote/SupabaseSdkClient.kt

# 2. SupabaseRepository.kt faylini toza holda qaytadan yozamiz
@"
package com.aicallblocker.app.data.repository

import com.aicallblocker.app.data.remote.SupabaseSdkClient
import io.github.jan-tennert.supabase.gotrue.auth
import io.github.jan-tennert.supabase.gotrue.user.UserInfo
import io.github.jan-tennert.supabase.postgrest.from
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
"@ | Out-File -Encoding utf8NoBOM app/src/main/java/com/aicallblocker/app/data/repository/SupabaseRepository.kt