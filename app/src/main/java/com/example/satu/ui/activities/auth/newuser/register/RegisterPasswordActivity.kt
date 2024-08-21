package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityLoginEmailBinding
import com.example.satu.databinding.ActivityRegisterPasswordBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPasswordBinding
    private lateinit var email: String
    private lateinit var phone: String
    private var rekening: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("EMAIL") ?: ""
        phone = intent.getStringExtra("PHONE") ?: ""
        rekening = intent.getLongExtra("REKENING", -1L)

        val fullText = "Masukkan Email dan Kata Sandi untuk mengakses M-Banking SATU"
        val spannableString = SpannableString(fullText)

        val primaryColor = ContextCompat.getColor(this, R.color.primary)
        val startIndex = fullText.indexOf("M-Banking SATU")
        val endIndex = startIndex + "M-Banking SATU".length

        spannableString.setSpan(
            ForegroundColorSpan(primaryColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvText5.text = spannableString

        // Ganti findViewById dengan binding
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnNext.setOnClickListener {
            if (validateInput()) {
                val mainIntent = Intent(this@RegisterPasswordActivity, RegisterPinActivity::class.java).apply {
                    putExtra("EMAIL", email)
                    putExtra("PHONE", phone)
                    putExtra("REKENING", rekening)
                    putExtra("PASSWORD", binding.etPassword.text.toString())
                }
                startActivity(mainIntent)
            }
        }

        binding.topAppBar.setOnClickListener {
            val intent = Intent(this, RegisterEmailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateInput(): Boolean {
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etKonfirmPassword.text.toString()

        return when {
            password.isEmpty() || confirmPassword.isEmpty() -> {
                showSnackbar("Semua kolom harus diisi")
                false
            }
            password != confirmPassword -> {
                showSnackbar("Password harus sama")
                false
            }
            !isPasswordValid(password) -> {
                showSnackbar("Password tidak memenuhi persyaratan")
                false
            }
            else -> true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val lengthValid = password.length >= 8
        val uppercaseValid = password.any { it.isUpperCase() }
        val lowercaseValid = password.any { it.isLowerCase() }
        val digitValid = password.any { it.isDigit() }
        val symbolValid = password.any { it in "!@#$%^&*()-_+=<>?[]{}|\\:;\"',./`~" }

        return lengthValid && uppercaseValid && lowercaseValid && digitValid && symbolValid
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
