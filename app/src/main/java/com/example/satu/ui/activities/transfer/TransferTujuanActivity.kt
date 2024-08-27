package com.example.satu.ui.activities.transfer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivityTransferTujuanBinding
import com.example.satu.ui.activities.MainActivity

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
            finish()
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferTujuanActivity, MainActivity::class.java))
        }
    }
}