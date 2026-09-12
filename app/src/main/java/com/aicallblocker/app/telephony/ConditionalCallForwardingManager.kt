package com.aicallblocker.app.telephony

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager

/**
 * GSM qo'ng'iroqni AI backend'ga "olib borish" uchun yagona real yo'l —
 * operator darajasidagi SHARTLI CALL FORWARDING (USSD kodlar orqali).
 * Bu Android ilovasining o'zi tomonidan DOIMIY yoqib qo'yiladigan narsa
 * emas — foydalanuvchi buni ANGLAGAN HOLDA, bir marta sozlashi kerak.
 *
 * MUHIM CHEKLOVLAR:
 *  - USSD kodlari operatorga qarab farq qiladi (quyida GSM standart —
 *    3GPP TS 22.030 — kodlar berilgan, ko'p operatorda ishlaydi, lekin
 *    100% kafolat yo'q; ba'zi mamlakat/operatorlarda o'chirilgan bo'lishi
 *    mumkin).
 *  - Bu kodni DASTURIY ravishda "sukut bo'yicha yoqib qo'yish" yomon
 *    amaliyot va foydalanuvchi maxfiyligini buzadi — shuning uchun bu
 *    funksiya faqat aniq foydalanuvchi tasdig'idan keyin chaqirilishi kerak.
 *  - `ACTION_DIAL` ishlatiladi: ilova USSD'ni avtomatik yubormaydi, oxirgi
 *    tasdiqni foydalanuvchi telefon ilovasida beradi.
 *
 * Amaliy oqim:
 *  1) Foydalanuvchi ilovada "Shubhali qo'ng'iroqlarni AI assistentga
 *     yo'naltirish" funksiyasini YOQADI (bir martalik sozlash, tushuntirish
 *     bilan).
 *  2) Ilova operatorga CFNRy (javob berilmasa) yoki CFB (band bo'lsa)
 *     shartli forwarding'ni virtual raqamga (backend/Twilio raqami)
 *     sozlaydi.
 *  3) Endi spam raqamlar tomonidan qilingan va foydalanuvchi javob
 *     bermagan/rad etgan qo'ng'iroqlar avtomatik virtual raqamga tushadi,
 *     u yerda AI ovozli assistent javob beradi.
 *  4) `MyCallScreeningService` spam skorini oldindan aniqlab, agar juda
 *     yuqori bo'lsa, qo'ng'iroqni REJECT qiladi (allaqachon mavjud logika) —
 *     bu holda forwarding kerak emas. Forwarding faqat "noaniq" holatlar
 *     (masalan yangi/tanilmagan raqamlar javobsiz qolganda) uchun foydali.
 */
object ConditionalCallForwardingManager {

    // 3GPP standart USSD kodlar (ko'p GSM operatorda ishlaydi):
    // CFNRy — Call Forwarding on No Reply
    private const val USSD_ENABLE_CFNRY = "**61*%s*11*20#"
    private const val USSD_DISABLE_CFNRY = "##61#"
    // CFB — Call Forwarding on Busy
    private const val USSD_ENABLE_CFB = "**67*%s#"
    private const val USSD_DISABLE_CFB = "##67#"

    fun enableCodesFor(virtualNumber: String): List<String> {
        val normalized = normalizeE164(virtualNumber)
        return listOf(
            String.format(USSD_ENABLE_CFNRY, normalized),
            String.format(USSD_ENABLE_CFB, normalized)
        )
    }

    fun disableCodes(): List<String> = listOf(
        USSD_DISABLE_CFNRY,
        USSD_DISABLE_CFB
    )

    fun openDialer(context: Context, ussdCode: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:${Uri.encode(ussdCode)}")
        }
        context.startActivity(intent)
    }

    private fun normalizeE164(value: String): String {
        val normalized = value.replace(" ", "").replace("-", "").replace("(", "").replace(")", "")
        require(Regex("^\\+[1-9]\\d{6,14}$").matches(normalized)) {
            "Virtual number must use E.164 format"
        }
        return normalized
    }

    /** Joriy operator forwarding holatini tekshirish — ba'zi operatorlarda ishlamasligi mumkin. */
    fun getCarrierName(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkOperatorName
    }
}
