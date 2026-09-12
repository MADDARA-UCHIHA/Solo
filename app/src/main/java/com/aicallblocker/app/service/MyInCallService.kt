package com.aicallblocker.app.service

import android.telecom.Call
import android.telecom.InCallService

/**
 * YO'L A skeleti — real qo'ng'iroq audiosiga qonuniy kirish uchun.
 *
 * Bu klass ishlashi uchun:
 *  1. Manifestda `<service>` sifatida `BIND_INCALL_SERVICE` ruxsati bilan
 *     e'lon qilinishi kerak (AndroidManifest.xml'dagi izohga qarang).
 *  2. Foydalanuvchi ilovani **Default Phone App** qilib tanlashi kerak —
 *     `RoleManager.ROLE_DIALER`.
 *  3. Faqat shundan keyin `onCallAdded()` chaqiriladi va `Call` obyekti
 *     orqali tizim tomonidan boshqariladigan audio route'ga ulanish mumkin
 *     bo'ladi.
 *
 * ESLATMA: Hatto default dialer bo'lgan taqdirda ham, operator tarmog'idagi
 * (PSTN) qo'ng'iroqning xom audio signalini arbitrar tarzda "ushlab olish"
 * barcha OEM/Android versiyalarida bir xilda ishlamasligi mumkin — bu qism
 * qurilma va Android versiyasiga qarab qo'shimcha test talab qiladi.
 */
class MyInCallService : InCallService() {

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        // TODO: call.registerCallback(...) orqali holat o'zgarishlarini kuzatish
        // TODO: Kerak bo'lsa AiCallHandlerService'ni shu yerdan ishga tushirish,
        //       chunki endi bu servis "haqiqiy" qo'ng'iroq egasi hisoblanadi.
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        // TODO: sessiyani yakunlash, WebSocket'ni yopish
    }
}
