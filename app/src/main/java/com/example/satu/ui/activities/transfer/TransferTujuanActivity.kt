package com.example.satu.ui.activities.transfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferTujuanBinding

class TransferTujuanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferTujuanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferTujuanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        btnTfBca.setOnClickListener {
            startActivity(Intent(this@TransferTujuanActivity, TransferBcaActivity::class.java))
        }
    }
}