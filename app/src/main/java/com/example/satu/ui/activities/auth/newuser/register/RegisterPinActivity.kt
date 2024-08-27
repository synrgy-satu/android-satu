package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterPinBinding
import com.example.satu.ui.factory.AuthViewModelFactory
import com.example.satu.ui.viewmodel.RegisterViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.google.android.material.snackbar.Snackbar

class RegisterPinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPinBinding
    private lateinit var email: String
    private lateinit var phone: String
    private lateinit var password: String
    private var rekening: Long = -1L

    private val viewModel: RegisterViewModel by viewModels {
        AuthViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("EMAIL") ?: ""
        phone = intent.getStringExtra("PHONE") ?: ""
        password = intent.getStringExtra("PASSWORD") ?: ""
        rekening = intent.getLongExtra("REKENING", -1L)

        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            val pin = etPassword.text.toString()
            val confirmPin = etPasswordKonfirm.text.toString()
            if (email.isEmpty() || phone.isEmpty() || password.isEmpty() || rekening == -1L) {
                showSnackBar("Data yang diperlukan tidak lengkap. Silakan coba lagi.")
            }
            when {
                pin.isEmpty() || confirmPin.isEmpty() -> {
                    showSnackBar("Harap isi semua kolom PIN.")
                }
                pin.length < 6 || confirmPin.length < 6 -> {
                    showSnackBar("PIN harus terdiri dari minimal 6 digit.")
                }
                pin != confirmPin -> {
                    showSnackBar("PIN tidak cocok. Silakan periksa kembali.")
                }
                else -> {

                    Log.d("OKE REKENING: ", rekening.toString())
                    Log.d("OKE EMAIL: ", email.toString())
                   viewModel.register(email, password, rekening, phone, pin ).observe(this@RegisterPinActivity) { result ->
                        when (result) {
                            is com.example.common.Result.Loading -> ProgressDialogUtils.showProgressDialog(this@RegisterPinActivity)
                            is com.example.common.Result.Success -> onRegisterSuccess()
                            is com.example.common.Result.Error -> onRegisterError(result.error)
                        }
                    }
                }
            }
        }

        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterPinActivity, RegisterPasswordActivity::class.java))
            finish()
        }
    }


    private fun onRegisterSuccess() {
        ProgressDialogUtils.hideProgressDialog()
        startActivity(Intent(this@RegisterPinActivity, RegisterSuccessActivity::class.java))
        finish()
    }

    private fun onRegisterError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }
    private fun showSnackBar(message: String) {
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_LONG).show()
    }
}