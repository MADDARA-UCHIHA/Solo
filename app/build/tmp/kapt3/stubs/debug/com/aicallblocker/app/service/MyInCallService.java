package com.aicallblocker.app.service;

/**
 * YO'L A skeleti — real qo'ng'iroq audiosiga qonuniy kirish uchun.
 *
 * Bu klass ishlashi uchun:
 * 1. Manifestda `<service>` sifatida `BIND_INCALL_SERVICE` ruxsati bilan
 *    e'lon qilinishi kerak (AndroidManifest.xml'dagi izohga qarang).
 * 2. Foydalanuvchi ilovani **Default Phone App** qilib tanlashi kerak —
 *    `RoleManager.ROLE_DIALER`.
 * 3. Faqat shundan keyin `onCallAdded()` chaqiriladi va `Call` obyekti
 *    orqali tizim tomonidan boshqariladigan audio route'ga ulanish mumkin
 *    bo'ladi.
 *
 * ESLATMA: Hatto default dialer bo'lgan taqdirda ham, operator tarmog'idagi
 * (PSTN) qo'ng'iroqning xom audio signalini arbitrar tarzda "ushlab olish"
 * barcha OEM/Android versiyalarida bir xilda ishlamasligi mumkin — bu qism
 * qurilma va Android versiyasiga qarab qo'shimcha test talab qiladi.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2 = {"Lcom/aicallblocker/app/service/MyInCallService;", "Landroid/telecom/InCallService;", "()V", "onCallAdded", "", "call", "Landroid/telecom/Call;", "onCallRemoved", "app_debug"})
public final class MyInCallService extends android.telecom.InCallService {
    
    public MyInCallService() {
        super();
    }
    
    @java.lang.Override()
    public void onCallAdded(@org.jetbrains.annotations.NotNull()
    android.telecom.Call call) {
    }
    
    @java.lang.Override()
    public void onCallRemoved(@org.jetbrains.annotations.NotNull()
    android.telecom.Call call) {
    }
}