package com.example.satu.ui.activities.transfer

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferConfirmationBinding
import com.example.satu.databinding.ActivityTransferReceiptBinding

class TransferReceiptActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}