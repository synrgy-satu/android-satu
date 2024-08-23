package com.example.satu.ui.activities.transfer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferConfirmationBinding
import com.example.satu.databinding.ActivityTransferTujuanBinding
import com.example.satu.ui.MainActivity
import java.text.NumberFormat
import java.util.Locale

class TransferConfirmationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("full_name", "")
        val rekeningNumber = sharedPref.getString("rekening_number", "")
        val cardNumber = sharedPref.getString("card_number", "")

        val sharedPrefTFRekning = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
        val targetRekeningNumber = sharedPrefTFRekning .getString("targetRekeningNumber", "")
        val nameTujuan = sharedPrefTFRekning .getString("namaRekeningTujuan", "")


        val sharedPrefTF = getSharedPreferences("UserTransfer", Context.MODE_PRIVATE)
        val nominalString = sharedPrefTF.getString("nominal", "")
        val catatan = sharedPrefTF.getString("catatan", "")

        val nominal = nominalString?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedNominal= numberFormat.format(nominal)

        binding.tvNama.text = nameTujuan
        binding.tvCardNumber.text = targetRekeningNumber
        binding.tvTypeRekening.text = "Saver ($rekeningNumber)"
        binding.tvNominal.text = "IDR $formattedNominal"
        binding.tvCatatan.text = catatan

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferConfirmationActivity, TransferNowActivity::class.java))
            finish()
        }

        btnConfirm.setOnClickListener {
            startActivity(Intent(this@TransferConfirmationActivity, TransferVerificationActivity::class.java))
            finish()
        }
    }
}