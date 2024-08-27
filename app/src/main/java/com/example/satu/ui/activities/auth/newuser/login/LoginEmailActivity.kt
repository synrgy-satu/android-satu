package com.example.satu.ui.activities.auth.newuser.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityLoginEmailBinding
import com.example.satu.ui.activities.auth.forgotpassword.ForgotPasswordActivity
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.LoginViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.SnackbarUtils

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

        setSpannableText()
        setupClickListeners()
    }

    private fun setSpannableText() {
        val fullText = "Masukkan Email dan Kata Sandi untuk mengakses M-Banking SATU"
        val spannableString = SpannableString(fullText).apply {
            val primaryColor = ContextCompat.getColor(this@LoginEmailActivity, R.color.primary)
            val startIndex = fullText.indexOf("M-Banking SATU")
            val endIndex = startIndex + "M-Banking SATU".length
            setSpan(ForegroundColorSpan(primaryColor), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.tvText5.text = spannableString
    }

    private fun setupClickListeners() = with(binding) {
        btnLanjut.setOnClickListener { handleLogin() }
        tvForgotPasswordLogin.setOnClickListener { navigateTo(ForgotPasswordActivity::class.java) }
        topAppBar.setOnClickListener { navigateTo(OnBoardingNewUserActivity::class.java) }
    }

    private fun handleLogin() {
        if (validateInputs()) {
            viewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is com.example.common.Result.Loading -> ProgressDialogUtils.showProgressDialog(this)
                    is com.example.common.Result.Success -> onLoginSuccess()
                    is com.example.common.Result.Error -> onLoginError(result.error)
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        email = binding.etEmail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()

        return when {
            email.isEmpty() -> showError("Email wajib diisi")
            password.isEmpty() -> showError("Password wajib diisi")
            !isValidEmail(email) -> showError("Format email salah")
            else -> true
        }
    }

    private fun isValidEmail(email: String) = email.matches(Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))

    private fun onLoginSuccess() {
        ProgressDialogUtils.hideProgressDialog()
        saveToSharedPreferences("user_password", password)
        navigateTo(LoginSuccessActivity::class.java, true)
    }

    private fun onLoginError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }

    private fun showError(message: String): Boolean {
        showSnackBar(message)
        return false
    }

    private fun saveToSharedPreferences(key: String, value: String) {
        getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .edit()
            .putString(key, value)
            .apply()
    }

    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }

    private fun navigateTo(activityClass: Class<*>, finishCurrent: Boolean = false) {
        startActivity(Intent(this, activityClass))
        if (finishCurrent) finish()
    }
}
