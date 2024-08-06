package com.example.satu.ui.activities.maintance

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.MainActivity
import com.example.satu.R
import com.example.satu.databinding.ActivityMaintanceBinding
import com.example.satu.databinding.ActivityRegisterEmailBinding
import com.example.satu.ui.activities.auth.newuser.register.RegisterRekeningActivity
import com.example.satu.ui.activities.loader.LoaderRegisterEmailActivity

class MaintanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaintanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            startActivity(Intent(this@MaintanceActivity, MainActivity::class.java))
            finish()
        }
    }
}