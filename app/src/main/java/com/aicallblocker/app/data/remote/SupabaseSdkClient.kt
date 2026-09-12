package com.aicallblocker.app.data.remote

import com.aicallblocker.app.BuildConfig
import io.github.jan-tennert.supabase.createSupabaseClient
import io.github.jan-tennert.supabase.gotrue.Auth
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