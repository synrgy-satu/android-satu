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
import java.text.NumberFormat
import java.util.Locale

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
        val balanceString = sharedPref.getString("balance", "")

        // Format balance
        val balance = balanceString?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedBalance = numberFormat.format(balance)

        binding.tvName.text = fullName
        binding.tvCardNumber.text = cardNumber
        binding.tvRekeningSumber.text = "Saver ($rekeningNumber)"
        binding.tvSaldo.text = "Saldo: IDR $formattedBalance"
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferNowActivity, TransferBcaActivity::class.java))
        }
    }
}