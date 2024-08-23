package com.example.satu.ui.activities.transfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferBcaActivituBinding
import com.example.satu.databinding.ActivityTransferTujuanBinding

class TransferBcaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferBcaActivituBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferBcaActivituBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        btnAdd.setOnClickListener {
            startActivity(Intent(this@TransferBcaActivity, TransferAddNewRekeningActivity::class.java))
            finish()
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferBcaActivity, TransferTujuanActivity::class.java))
            finish()
        }
    }
}