package com.example.satu.ui.activities.auth.newuser.login

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
import com.example.satu.databinding.ActivityOnBoardingNewUserBinding
import com.example.satu.ui.activities.auth.forgotpassword.ForgotPasswordActivity

class LoginEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
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
        btnLanjut.setOnClickListener {
            startActivity(Intent(this@LoginEmailActivity, LoginSuccessActivity::class.java))
            finish()
        }
        tvForgotPasswordLogin.setOnClickListener {
            startActivity(Intent(this@LoginEmailActivity, ForgotPasswordActivity::class.java))
        }
    }
}