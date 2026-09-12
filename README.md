# AI Call Blocker — Boshlang'ich Loyiha (Skeleton)

Bu — Android Studio'da ochish uchun tayyor **boshlang'ich** Kotlin loyihasi.
To'liq ishlaydigan, do'konga tayyor ilova EMAS.

## Tanlangan yakuniy arxitektura: YO'L B (VoIP/Virtual raqam)

Muhokama natijasida quyidagi arxitektura tanlandi, chunki u Android'ning
GSM audio cheklovlarini butunlay chetlab o'tadi:

```
Qo'ng'iroq qiluvchi (GSM)
        │
        ▼  shartli call forwarding (operator USSD, faqat foydalanuvchi
        │  tasdig'idan keyin — ConditionalCallForwardingManager)
Virtual raqam (Twilio/Vonage/o'z SIP trunk'ingiz)
        │  WebRTC/SIP — to'liq nazoratda, Android bilan aloqasi yo'q
        ▼
Backend (ALOHIDA SERVER, bu repo doirasida emas):
   STT → LLM → Neural TTS → AI qo'ng'iroqqa javob beradi
        │
        ▼  faqat matn/JSON (VoipBackendApi shartnomasi)
FCM push → Android ilova (VoipPushService)
        │
        ▼
NotificationHelper → foydalanuvchiga xulosa
```

**Nega bu yaxshiroq:** Android ilova xom audio bilan HECH QACHON ishlamaydi.
Shuning uchun `AudioRecord`, `InCallService`, Default Dialer, OEM audio
cheklovlari — buларning HECH biri kerak emas. Bitta yagona "og'ir" qism —
operator darajasidagi shartli forwarding — bu ham ilova tomonidan
DASTURIY ravishda emas, foydalanuvchi ONG'IDA bosgan tugma orqali,
standart GSM USSD mexanizmi bilan amalga oshiriladi.

### Yangi/o'zgargan fayllar
- `telephony/ConditionalCallForwardingManager.kt` — USSD orqali shartli
  forwarding yoqish/o'chirish (**faqat aniq foydalanuvchi tasdig'idan keyin
  chaqiring** — sukut bo'yicha yoqilmaydi, hech qayerda avtomatik
  chaqirilmaydi).
- `data/remote/VoipBackendApi.kt` — Android va SIZ QURADIGAN backend
  o'rtasidagi API shartnomasi (forwarding ro'yxatdan o'tkazish, sessiya
  tarixini olish).
- `service/VoipPushService.kt` — FCM orqali backend'dan AI suhbat
  xulosasini qabul qilish va notification chiqarish.
- `service/AiCallHandlerService.kt` — **ENDI ASOSIY OQIMDA ISHLATILMAYDI**
  (fayl ichidagi izohga qarang). Faqat kelajakda ilova ichidagi alohida
  "AI bilan to'g'ridan-to'g'ri suhbat" funksiyasi uchun namuna sifatida
  qoldirilgan.
- `service/MyInCallService.kt` — YO'L A (InCallService) skeleti,
  **ishlatilmaydi**, faqat kelajakda kerak bo'lib qolsa deb saqlangan.

## Muhim tushuntirish: forwarding aslida qanday "faqat spamga" ishlaydi

Ilk qarashda savol tug'iladi: *"axir spam qiluvchi ko'pincha to'g'ridan-to'g'ri
odamning haqiqiy raqamiga qo'ng'iroq qiladi-ku, forwarding buni qanday tutadi?"*

Javob: CFNRy/CFB — operator darajasidagi **umumiy, statik** qoida ("javob
berilmasa/band bo'lsa — HAR DOIM shu raqamga forward qil"), operator
qo'ng'iroq qiluvchini farqlamaydi. "Faqat spamni forward qilish" effekti
ilova tomonida quyidagicha yaratiladi:

- Xavfsiz/tanish raqamlar → odatdagidek jiringlaydi → foydalanuvchi javob
  beradi → forwarding hech qachon ishga tushmaydi.
- Qora ro'yxatdagi raqamlar → oddiy REJECT qilinadi (forwarding shart emas).
- **Noaniq/tanilmagan raqamlar** → `CallScreeningService.setSilenceCall(true)`
  bilan JIM qilinadi (ko'rinadi, lekin jiringlamaydi) → "javobsiz qoladi" →
  CFNRy timeout'i (5-30s, operatorga bog'liq) o'tgach, operator uni
  AVTOMATIK virtual raqamga (AI'ga) yo'naltiradi.

Ya'ni farqlash operator emas, ilova darajasida — kim jiringlaydi va kim
jim qolib "javobsiz" hisoblanadi, shuni `MyCallScreeningService` hal
qiladi. Trade-off: ~5-30s kechikish, va noaniq holatlarda haqiqiy tanish
odam ham bir marta "javobsiz qolgandek" tuyulishi mumkin (u holda AI unga
javob berib, keyin foydalanuvchiga xulosa yuboradi — odam butunlay
e'tiborsiz qolmaydi).

## MUHIM — real loyihaga o'tishdan oldin bajarish kerak bo'lganlar



1. **Backend — bu repoda YO'Q.** `VoipBackendApi` faqat shartnoma
   (interface). Haqiqiy server (Twilio/Vonage webhook qabul qilish, STT/LLM/
   Neural TTS orkestratsiyasi, FCM push yuborish) alohida loyiha sifatida
   yozilishi kerak — bu odatda Node.js/Python/Go'da, Android'dan mustaqil
   ishlab chiqiladi.

2. **USSD forwarding — operatorga qarab ishlamasligi mumkin.**
   `ConditionalCallForwardingManager`dagi kodlar 3GPP standartiga asoslangan
   (ko'p GSM operatorda ishlaydi), lekin ba'zi mamlakat/operator/eSIM
   holatlarida farq qilishi yoki umuman ishlamasligi mumkin — buni har bir
   maqsadli bozor uchun qo'lda test qilish kerak.

3. **Forwarding — foydalanuvchi maxfiyligiga sezgir amal.** Bu funksiya
   HECH QACHON avtomatik/sukut bo'yicha yoqilmasligi kerak. UI'da aniq
   tushuntirish va yoqish/o'chirish tugmasi bo'lishi shart (hozircha bu UI
   yozilmagan — faqat manager klassi bor).

4. **Twilio Voice SDK versiyasi** (`build.gradle.kts`dagi `6.9.0`) —
   loyihani ochishdan oldin Twilio'ning rasmiy hujjatidan eng so'nggi
   barqaror versiyani tekshiring, chunki bu qiymat yozilgan vaqtdagi
   namuna sifatida qo'yilgan.

5. **`google-services.json` yo'q.** Firebase Cloud Messaging ishlashi uchun
   Firebase Console'da loyiha yaratib, `app/google-services.json` faylini
   qo'shishingiz shart — aks holda Gradle sync xatolik beradi.

6. **Neural TTS tanlovi backend tomonda.** ElevenLabs, Google Cloud TTS,
   Azure Neural TTS, Amazon Polly Neural — barchasi server SDK/API orqali
   ishlatiladi, Android bunga aloqasi yo'q endi.

7. **Hilt DI to'liq ulanmagan**, `@Inject` modullar yozilmagan.

8. Kod **kompilyatsiya/test qilinmagan** — namuna/skelet sifatida
   yozilgan, Gradle sync'dan keyin kichik tuzatishlar kerak bo'lishi mumkin.

## Keyingi qadamlar bo'yicha tavsiya
Har bir bo'limni (masalan, faqat call screening, keyin faqat Room+cloud
sync, keyin AI voice) alohida-alohida ishga tushirib test qiling — hammasini
birdan ishga tushirishga urinish debug qilishni qiyinlashtiradi.

## Qurilma ichidagi tahlil va offline rejim

- `LocalSpeechRecognizer` Android `SpeechRecognizer` API'sidan foydalanadi va
  `EXTRA_PREFER_OFFLINE=true` bilan ishlaydi. Bu foydalanuvchi tugma bosib
  boshlagan mikrofon transkripsiyasi uchun mo'ljallangan; GSM qo'ng'iroq
  audiosini yashirincha yozib olmaydi. Natija qurilmada `CallTranscriptAnalyzer`
  orqali kalit so'zlar, maxfiy ma'lumot so'rovi va bosim/tahdid ohangi bo'yicha
  baholanadi.
- `call_records` Room jadvali bloklangan qo'ng'iroqlar tarixi va sabablarini
  saqlaydi. `NumberEntity` esa oq/qora ro'yxatni offline ishlatishda davom
  ettiradi.
- Foydalanuvchi raqamni spam deb belgilasa, avval lokal bazaga yoziladi,
  internet mavjud bo'lsa `POST /v1/spam/report` orqali community bazaga
  yuboriladi. Tarmoq xatosi bloklashni bekor qilmaydi.
- JSON va CSV backup/import faqat foydalanuvchi tanlagan fayl URI'siga yozadi;
  backup avtomatik ravishda serverga yuborilmaydi. Kontaktlarni oq ro'yxatga
  kiritish alohida `READ_CONTACTS` ruxsatini talab qiladi.
- `AiCallHandlerService` xom audio yuborishni sukut bo'yicha o'chiradi.
  `sendMicChunk` faqat ilova ichidagi aniq `setCloudVoiceConsent(true)` roziligi
  berilgandan keyin ishlaydi. `LocalSpeechRecognizer` natijasi esa UI'da
  qurilmada tahlil qilinadi va serverga yuborilmaydi.
- Yuqori xavfli mahalliy tahlilda qurilma vibratsiyasi va qisqa alert tone
  beriladi. Android 14 uchun `FOREGROUND_SERVICE_DATA_SYNC` deklaratsiyasi
  qo'shilgan; `AiCallHandlerService` ishlatilsa, uning doimiy notification'i
  foydalanuvchiga ko'rinadi.
- `AiCallHandlerService` real PSTN mikrofonini yozmaydi va InCallService/default
  dialer emas. Shu sababli manifestda `microphone` yoki `phoneCall` turi
  ataylab e'lon qilinmagan: Android 14 bu turlar uchun maxsus ruxsatlar va
  tegishli rolni talab qiladi. `LocalSpeechRecognizer` esa foydalanuvchi
  ekranda tugmani bosgan paytda Activity ichida ishlaydi.
