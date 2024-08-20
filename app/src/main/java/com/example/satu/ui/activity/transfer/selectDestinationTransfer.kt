package com.example.satu.ui.activity.transfer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivitySelectDestinationTransferBinding

class selectDestinationTransfer : AppCompatActivity() {
    private lateinit var binding : ActivitySelectDestinationTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectDestinationTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        btnTfBca.setOnClickListener {
            startActivity(Intent(this@selectDestinationTransfer, transferRekBca::class.java))
        }
    }
}