package com.example.satu.ui.activities.maintance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.ui.MainActivity
import com.example.satu.databinding.ActivityMaintanceBinding

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