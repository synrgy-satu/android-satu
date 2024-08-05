package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterPinBinding
import com.example.satu.databinding.ActivityRegisterSuccessBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity

class RegisterSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityRegisterSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnMasuk.setOnClickListener {
            startActivity(Intent(this@RegisterSuccessActivity, LoginEmailActivity::class.java))
            finish()
        }
    }
}