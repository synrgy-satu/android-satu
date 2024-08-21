package com.example.satu.ui.activities.auth.repeateduser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterSuccessBinding
import com.example.satu.databinding.ActivityRepeadLoginBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginSuccessActivity
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.SnackbarUtils

class RepeadLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRepeadLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityRepeadLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fullText = "Masukkan Kata Sandi M-Banking SATU"
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
        binding.btnLogin.setOnClickListener {
            val inputPassword = binding.etPassword.text.toString().trim()

            if (inputPassword.isEmpty()) {
                showSnackBar("Wajib mengisi kata sandi")
            } else {
                checkPassword(inputPassword)
            }
        }
    }
    private fun checkPassword(inputPassword: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedPassword = sharedPreferences.getString("user_password", "")

        if (inputPassword == savedPassword) {
            ProgressDialogUtils.showProgressDialog(this)
            Handler(Looper.getMainLooper()).postDelayed({
                ProgressDialogUtils.hideProgressDialog()
                startActivity(Intent(this@RepeadLoginActivity, LoginSuccessActivity::class.java))
                finish()
            }, 500)
        }  else {
            showSnackBar("Kata sandi salah")
        }
    }



    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }
}