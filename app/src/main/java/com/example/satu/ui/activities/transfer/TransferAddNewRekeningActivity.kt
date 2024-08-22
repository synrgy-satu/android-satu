package com.example.satu.ui.activities.transfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferAddNewRekeningBinding
import com.example.satu.databinding.ActivityTransferBcaActivituBinding

class TransferAddNewRekeningActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferAddNewRekeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferAddNewRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        btnNext.setOnClickListener {
            startActivity(Intent(this@TransferAddNewRekeningActivity, TransferBcaActivity::class.java))
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferAddNewRekeningActivity, TransferTujuanActivity::class.java))
        }
    }
}