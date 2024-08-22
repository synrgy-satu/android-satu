package com.example.satu.ui.activity.qris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivityScanQrAddNominalBinding
import com.example.satu.ui.activity.transfer.verificationTransaction

class scanQrAddNominal : AppCompatActivity() {
    private lateinit var binding: ActivityScanQrAddNominalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanQrAddNominalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@scanQrAddNominal, cameraPermission::class.java))
        }

        btnPay.setOnClickListener {
            startActivity(Intent(this@scanQrAddNominal, verificationTransaction::class.java))
        }
    }
}