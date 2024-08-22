package com.example.satu.ui.activity.qris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.databinding.ActivityPayWithCodeBinding
import com.example.satu.ui.activity.transfer.transferRekBca
import com.example.satu.ui.activity.transfer.verificationTransaction

class PayWithCode : AppCompatActivity() {
    private lateinit var binding: ActivityPayWithCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayWithCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@PayWithCode, cameraPermission::class.java))
        }

        btnCode.setOnClickListener {
            startActivity(Intent(this@PayWithCode, verificationTransaction::class.java))
        }
    }
}