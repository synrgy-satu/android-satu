package com.example.satu.ui.activity.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.api.ApiConfig
import com.example.satu.data.model.request.auth.ForgotPasswordRequest
import com.example.satu.data.model.response.auth.ForgotPasswordResponse
import com.example.satu.databinding.ActivityForgotpasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotpasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        btnKirim.setOnClickListener {
            val email = etEmail.text.toString()

            if (email.isNotEmpty()) {
                sendForgotPasswordRequest(email)
            } else {
                Toast.makeText(this@ForgotPasswordActivity, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        topAppBar.setOnClickListener {
            finish()
        }
    }

    private fun sendForgotPasswordRequest(email: String) {
        val request = ForgotPasswordRequest(emailAddress = email)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiConfig.getApiService().forgotPassword(request)
                withContext(Dispatchers.Main) {
                    handleResponse(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ForgotPasswordActivity, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun handleResponse(response: ForgotPasswordResponse) {
        if (response.status == true) {
            Toast.makeText(this@ForgotPasswordActivity, "Permintaan berhasil, periksa email Anda", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@ForgotPasswordActivity, "Permintaan gagal: ${response.message}", Toast.LENGTH_LONG).show()
        }
    }
}
