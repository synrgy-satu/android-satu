package com.example.satu.ui.activity.transfer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityAddNewNumberRekeningBinding
import com.example.satu.databinding.ActivityConfirmationTransferBinding

class confirmationTransfer : AppCompatActivity() {
    private lateinit var binding: ActivityConfirmationTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@confirmationTransfer, transferNow::class.java))
        }
        btnConfirm.setOnClickListener {
            startActivity(Intent(this@confirmationTransfer, verificationTransaction::class.java))
        }
    }
}