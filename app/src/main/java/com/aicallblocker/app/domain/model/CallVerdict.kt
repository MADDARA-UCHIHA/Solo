package com.aicallblocker.app.domain.model

sealed class CallVerdict {
    data class Blacklisted(val reason: String) : CallVerdict()
    object Whitelisted : CallVerdict()
    data class Unknown(val spamScore: Float) : CallVerdict()
}
