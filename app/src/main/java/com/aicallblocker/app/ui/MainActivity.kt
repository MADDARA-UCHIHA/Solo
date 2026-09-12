package com.aicallblocker.app.ui

import android.app.AlertDialog
import android.app.role.RoleManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.aicallblocker.app.data.repository.CallRepository
import com.aicallblocker.app.data.repository.SupabaseRepository
import com.aicallblocker.app.service.CallDashboardStateStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var repository: CallRepository
    private lateinit var content: FrameLayout
    private lateinit var accountLabel: TextView
    private lateinit var statusLabel: TextView
    private val supabaseRepository = SupabaseRepository()
    private val navigationButtons = mutableMapOf<String, Button>()
    private val preferences by lazy {
        getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private val roleRequestLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = CallRepository.getInstance(applicationContext)
        ensureInstallIdentity()
        buildShell()
        showHome()

        lifecycleScope.launch {
            CallDashboardStateStore.current.collect { state ->
                statusLabel.text = state.status
            }
        }
    }

    private fun ensureInstallIdentity() {
        if (preferences.getString(KEY_USER_ID, null) == null) {
            preferences.edit()
                .putString(KEY_USER_ID, "AC-${UUID.randomUUID().toString().take(8).uppercase()}")
                .apply()
        }
    }

    private fun buildShell() {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(244, 247, 251))
        }
        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(24), dp(24), dp(20))
            background = roundedBackground(Color.rgb(20, 43, 78), 0, 0, 22)
        }
        header.addView(TextView(this).apply {
            text = "AI CALL BLOCKER"
            textSize = 22f
            setTextColor(Color.WHITE)
            setTypeface(null, android.graphics.Typeface.BOLD)
        })
        header.addView(TextView(this).apply {
            text = "Private protection for every call"
            textSize = 14f
            setTextColor(Color.rgb(205, 220, 238))
            setPadding(0, dp(6), 0, 0)
        })
        accountLabel = TextView(this).apply {
            textSize = 12f
            setTextColor(Color.rgb(205, 220, 238))
            setPadding(0, dp(12), 0, 0)
        }
        header.addView(accountLabel)
        root.addView(header)

        content = FrameLayout(this)
        root.addView(content, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        val navigation = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(dp(8), dp(8), dp(8), dp(8))
            setBackgroundColor(Color.WHITE)
        }
        navigation.addView(navButton("Home") { showHome() })
        navigation.addView(navButton("Plans") { showPlans() })
        navigation.addView(navButton("Settings") { showSettings() })
        root.addView(navigation)
        setContentView(root)
        refreshAccountLabel()
    }

    private fun navButton(title: String, action: () -> Unit): View {
        return Button(this).apply {
            text = title
            textSize = 12f
            isAllCaps = false
            setOnClickListener { action() }
            layoutParams = LinearLayout.LayoutParams(0, dp(52)).apply {
                weight = 1f
                setMargins(dp(4), 0, dp(4), 0)
            }
            navigationButtons[title] = this
        }
    }

    private fun showHome() {
        content.removeAllViews()
        selectTab("Home")
        val view = page()
        view.addPageChild(sectionTitle("Home"))
        view.addPageChild(card(
            "Protect your calls",
            "Screen unknown callers privately. Your recordings and transcripts stay off-device unless you explicitly enable a cloud feature."
        ))
        view.addPageChild(sectionTitle("Current protection"))
        statusLabel = TextView(this).apply {
            text = "Ready"
            textSize = 16f
            setTextColor(Color.rgb(20, 43, 78))
            setPadding(20, 12, 20, 12)
        }
        view.addPageChild(statusLabel)
        val activeNumber = preferences.getString(KEY_VIRTUAL_NUMBER, null)
        view.addPageChild(card(
            "Virtual number",
            activeNumber ?: "No virtual number connected"
        ))
        view.addPageChild(primaryButton(
            if (activeNumber == null) "Get a virtual number" else "Manage virtual number"
        ) { showPlans() })
        view.addPageChild(outlineButton("Set up call screening") { requestCallScreening() })
        view.addPageChild(sectionTitle("Privacy first"))
        view.addPageChild(TextView(this).apply {
            text = "Forwarding is never enabled silently. Every carrier action requires your confirmation in the phone dialer."
            textSize = 13f
            setTextColor(Color.DKGRAY)
            setPadding(20, 4, 20, 20)
        })
        content.addView(view)
    }

    private fun showPlans() {
        content.removeAllViews()
        selectTab("Plans")
        val view = page()
        view.addPageChild(sectionTitle("Plans & virtual numbers"))
        view.addPageChild(TextView(this).apply {
            text = "Choose a number. Nothing is charged until the backend confirms the order."
            textSize = 14f
            setTextColor(Color.DKGRAY)
            setPadding(20, 0, 20, 16)
        })
        view.addPageChild(planCard(
            "Random number",
            "$2.99 / month",
            "A low-cost number selected automatically.",
            "Choose random number"
        ) { showCheckout("Random number", "$2.99 / month", "Standard") })
        view.addPageChild(planCard(
            "Custom number",
            "$5.99 / month",
            "Search for an available number with your preferred area code.",
            "Search custom numbers"
        ) { showCustomNumberDialog() })
        view.addPageChild(card(
            "Routing status",
            when {
                preferences.getString(KEY_VIRTUAL_NUMBER, null) != null ->
                    "Virtual number active. Carrier forwarding remains off until you confirm it."
                preferences.getString(KEY_ORDER_STATUS, null) == "pending" ->
                    "Number request pending backend availability and payment confirmation."
                else -> "No order yet. Forwarding remains off by default."
            }
        ))
        content.addView(view)
    }

    private fun showSettings() {
        content.removeAllViews()
        selectTab("Settings")
        val view = page()
        view.addPageChild(sectionTitle("Settings"))
        view.addPageChild(accountCard())
        view.addPageChild(outlineButton("Permissions & call screening role") { requestAllPermissions() })
        view.addPageChild(outlineButton("Language: English") {
            Toast.makeText(this, "More languages will be available soon.", Toast.LENGTH_SHORT).show()
        })
        view.addPageChild(outlineButton("Privacy and data controls") { showPrivacyDialog() })
        view.addPageChild(outlineButton("Sign in / recover account") { showLoginDialog() })
        view.addPageChild(dangerButton("Delete account and local data") { confirmDeleteAccount() })
        content.addView(view)
    }

    private fun accountCard(): View {
        val id = preferences.getString(KEY_USER_ID, "Not available")
        accountLabel.text = "User ID: $id"
        val number = preferences.getString(KEY_VIRTUAL_NUMBER, "No active number")
        val email = preferences.getString(KEY_EMAIL, "Not connected")
        return card(
            "Account profile",
            "User ID: $id\nEmail: $email\nVirtual number: $number\nSubscription: Free\nExpires: Not subscribed\nCredits: 0"
        )
    }

    private fun showCustomNumberDialog() {
        val input = EditText(this).apply {
            hint = "Area code, e.g. 415"
            inputType = InputType.TYPE_CLASS_PHONE
        }
        AlertDialog.Builder(this)
            .setTitle("Search for a custom number")
            .setMessage("We will only search available numbers. Your choice is not reserved until you confirm the order.")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Search") { _, _ ->
                val areaCode = input.text.toString().trim()
                if (areaCode.length !in 2..6 || !areaCode.all { it.isDigit() }) {
                    Toast.makeText(this, "Enter a valid numeric area code.", Toast.LENGTH_SHORT).show()
                } else {
                    showCheckout("Custom number", "$5.99 / month", "VIP / Vanity")
                }
            }
            .show()
    }

    private fun showCheckout(type: String, price: String, tier: String) {
        AlertDialog.Builder(this)
            .setTitle("Checkout")
            .setMessage(
                "$type\nTier: $tier\nPrice: $price\n\n" +
                    "Choose a payment method below. The number is reserved only after the backend confirms payment."
            )
            .setSingleChoiceItems(arrayOf("Click / Payme", "Lemon Squeezy"), 0, null)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Continue securely") { _, _ ->
                lifecycleScope.launch {
                    runCatching {
                        supabaseRepository.createNumberOrder(tier.lowercase())
                    }.onSuccess {
                        preferences.edit().putString(KEY_ORDER_STATUS, "pending").apply()
                        Toast.makeText(
                            this@MainActivity,
                            "Order saved. Waiting for payment confirmation.",
                            Toast.LENGTH_LONG
                        ).show()
                        showPlans()
                    }.onFailure {
                        Toast.makeText(
                            this@MainActivity,
                            it.message ?: "Sign in before ordering a virtual number.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            .show()
    }

    private fun showLoginDialog() {
        val input = EditText(this).apply {
            hint = "Email address"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        AlertDialog.Builder(this)
            .setTitle("Sign in or recover your account")
            .setMessage("Your local ID remains on this device. Sign in connects it to your server account.")
            .setView(input)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Continue") { _, _ ->
                if (input.text.toString().contains("@")) {
                    preferences.edit().putString(KEY_EMAIL, input.text.toString().trim()).apply()
                    refreshAccountLabel()
                    Toast.makeText(this, "Account recovery request submitted.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Enter a valid email address.", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun showPrivacyDialog() {
        AlertDialog.Builder(this)
            .setTitle("Privacy and data")
            .setMessage("Cloud routing is opt-in. You can revoke forwarding with your carrier and delete local data at any time. Never share your account ID publicly.")
            .setPositiveButton("OK", null)
            .show()
    }

    private fun confirmDeleteAccount() {
        AlertDialog.Builder(this)
            .setTitle("Delete account and local data?")
            .setMessage("This removes the local ID, cached numbers, settings, and Room data from this device. It cannot cancel a carrier plan automatically.")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete") { _, _ ->
                deleteLocalAccount()
            }
            .show()
    }

    private fun deleteLocalAccount() {
        preferences.edit().clear().apply()
        deleteDatabase("ai_call_blocker.db")
        Toast.makeText(this, "Local account data deleted.", Toast.LENGTH_LONG).show()
        finishAndRemoveTask()
    }

    private fun requestAllPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.POST_NOTIFICATIONS
            ),
            REQUEST_CODE_PERMISSIONS
        )
        requestScreeningRole()
    }

    private fun requestCallScreening() = requestScreeningRole()

    private fun requestScreeningRole() {
        val roleManager = getSystemService(RoleManager::class.java)
        if (roleManager.isRoleAvailable(RoleManager.ROLE_CALL_SCREENING) &&
            !roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)
        ) {
            roleRequestLauncher.launch(
                roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
            )
        } else {
            Toast.makeText(this, "Call screening is already enabled.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshAccountLabel() {
        if (::accountLabel.isInitialized) {
            val id = preferences.getString(KEY_USER_ID, "Not available")
            val email = preferences.getString(KEY_EMAIL, null)
            accountLabel.text = "User ID: $id" + if (email != null) "  •  $email" else ""
        }
    }

    private fun page(): ScrollView = ScrollView(this).apply {
        isFillViewport = true
        addView(LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(18), dp(16), dp(28))
            tag = PAGE_CONTAINER
        })
    }

    private fun addToPage(view: ScrollView, child: View) {
        (view.getChildAt(0) as ViewGroup).addView(child)
    }

    private fun ScrollView.addPageChild(child: View) {
        addToPage(this, child)
    }

    private fun sectionTitle(text: String) = TextView(this).apply {
        this.text = text
        textSize = 24f
        setTypeface(null, android.graphics.Typeface.BOLD)
        setTextColor(Color.rgb(20, 43, 78))
        setPadding(dp(8), dp(4), dp(8), dp(14))
    }

    private fun card(title: String, body: String): View = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(20), dp(18), dp(20), dp(18))
        background = roundedBackground(Color.WHITE, 1, Color.rgb(224, 231, 240), 16)
        elevation = dp(2).toFloat()
        addView(TextView(context).apply {
            text = title
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(Color.rgb(20, 43, 78))
        })
        addView(TextView(context).apply {
            text = body
            textSize = 14f
            setTextColor(Color.DKGRAY)
            setPadding(0, dp(8), 0, 0)
        })
        layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, 0, 0, dp(14)) }
    }

    private fun planCard(title: String, price: String, body: String, action: String, click: () -> Unit): View {
        val container = card(title, "$price\n$body")
        (container as LinearLayout).addView(primaryButton(action, click))
        return container
    }

    private fun primaryButton(label: String, click: () -> Unit) = Button(this).apply {
        text = label
        setTextColor(Color.WHITE)
        isAllCaps = false
        background = roundedBackground(Color.rgb(29, 104, 187), 0, 0, 12)
        setOnClickListener { click() }
        layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, dp(8), 0, dp(10)) }
    }

    private fun outlineButton(label: String, click: () -> Unit) = Button(this).apply {
        text = label
        isAllCaps = false
        background = roundedBackground(Color.WHITE, 1, Color.rgb(205, 216, 230), 12)
        setOnClickListener { click() }
        layoutParams = LinearLayout.LayoutParams(-1, -2).apply { setMargins(0, 0, 0, dp(8)) }
    }

    private fun dangerButton(label: String, click: () -> Unit) = outlineButton(label, click).apply {
        setTextColor(Color.rgb(180, 35, 35))
    }

    private fun selectTab(selected: String) {
        navigationButtons.forEach { (title, button) ->
            button.background = roundedBackground(
                if (title == selected) Color.rgb(225, 238, 252) else Color.TRANSPARENT,
                0,
                0,
                12
            )
            button.setTextColor(
                if (title == selected) Color.rgb(20, 91, 160) else Color.rgb(90, 103, 118)
            )
        }
    }

    private fun roundedBackground(fill: Int, strokeWidth: Int, strokeColor: Int, radius: Int) =
        GradientDrawable().apply {
            setColor(fill)
            cornerRadius = dp(radius).toFloat()
            if (strokeWidth > 0) setStroke(dp(strokeWidth), strokeColor)
        }

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    companion object {
        const val EXTRA_NUMBER_TO_SHOW = "extra_number_to_show"
        const val EXTRA_DETAILS_TO_SHOW = "extra_details_to_show"
        private const val PREFERENCES_NAME = "aicallblocker_account"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_VIRTUAL_NUMBER = "virtual_number"
        private const val KEY_ORDER_STATUS = "order_status"
        private const val PAGE_CONTAINER = "page_container"
        private const val REQUEST_CODE_PERMISSIONS = 100
    }
}
