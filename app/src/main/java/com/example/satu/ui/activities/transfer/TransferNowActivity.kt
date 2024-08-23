package com.example.satu.ui.activities.transfer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferAddNewRekeningBinding
import com.example.satu.databinding.ActivityTransferNowBinding

class TransferNowActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferNowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("full_name", "")
        val rekeningNumber = sharedPref.getString("rekening_number", "")
        val cardNumber = sharedPref.getString("card_number", "")

        binding.tvName.text = fullName;
        binding.tvCardNumber.text = cardNumber;

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferNowActivity, TransferBcaActivity::class.java))
        }
    }
}