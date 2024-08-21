package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterEmailBinding
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.loader.LoaderRegisterEmailActivity
import com.example.satu.ui.activities.loader.LoaderRegisterRekeningActivity
import com.example.satu.utils.SnackbarUtils
import java.util.regex.Pattern

class RegisterEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            if (validateInputs()) {
                val email = etEmail.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val rekening = intent.getLongExtra("REKENING", -1L)
                val intent = Intent(this@RegisterEmailActivity, LoaderRegisterEmailActivity::class.java).apply {
                    putExtra("EMAIL", email)
                    putExtra("PHONE", phone)
                    putExtra("REKENING", rekening)
                }

                startActivity(intent)
            }
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterEmailActivity, RegisterRekeningActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()

        return when {
            email.isEmpty() -> {
                showSnackBar("Email wajib diisi")
                false
            }
            phone.isEmpty() -> {
                showSnackBar("Nomor Telepon wajib diisi")
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
}