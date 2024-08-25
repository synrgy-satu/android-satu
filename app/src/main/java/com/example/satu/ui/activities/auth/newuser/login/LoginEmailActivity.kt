package com.example.satu.ui.activities.auth.newuser.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityLoginEmailBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterSuccessActivity
import com.example.satu.ui.activity.auth.ForgotPasswordActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.ui.viewmodel.RegisterViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.Result
import com.example.satu.utils.SnackbarUtils
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class LoginEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var email: String
    private lateinit var password: String


    private val viewModel: LoginViewModel by viewModels {
        AuthViewModelFactory.getInstance(application)
    }
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
            if (validateInputs()) {
                viewModel.login(email, password).observe(this@LoginEmailActivity) { result ->
                    when (result) {
                        is Result.Loading -> ProgressDialogUtils.showProgressDialog(this@LoginEmailActivity)
                        is Result.Success -> onLoginSuccess()
                        is Result.Error -> onLoginError(result.error)
                    }
                }
            }
        }
        tvForgotPasswordLogin.setOnClickListener {
            startActivity(Intent(this@LoginEmailActivity, ForgotPasswordActivity::class.java))
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@LoginEmailActivity, OnBoardingNewUserActivity::class.java))
        }
    }

    private fun onLoginSuccess() {
        ProgressDialogUtils.hideProgressDialog()
        savePasswordToSharedPreferences(password)
        startActivity(Intent(this@LoginEmailActivity, LoginSuccessActivity::class.java))
        finish()
    }
    private fun onLoginError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }

    private fun validateInputs(): Boolean {
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        return when {
            email.isEmpty() -> {
                showSnackBar("Email wajib diisi")
                false
            }
            password.isEmpty() -> {
                showSnackBar("Password wajib diisi")
                false
            }
            !isValidEmail(email) -> {
                showSnackBar("Format email salah")
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        )
        return emailPattern.matcher(email).matches()
    }


    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }
    private fun savePasswordToSharedPreferences(password: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("user_password", password)
            apply()
        }
    }

}