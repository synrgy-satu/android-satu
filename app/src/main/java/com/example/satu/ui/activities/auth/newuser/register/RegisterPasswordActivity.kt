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

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            startActivity(Intent(this@RegisterPasswordActivity, RegisterPinActivity::class.java))
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterPasswordActivity, RegisterEmailActivity::class.java))
        }
    }
}