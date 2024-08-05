package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterPinBinding
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.activities.loader.LoaderRegisterRekeningActivity

class RegisterPinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            startActivity(Intent(this@RegisterPinActivity, RegisterSuccessActivity::class.java))
        }
    }
}