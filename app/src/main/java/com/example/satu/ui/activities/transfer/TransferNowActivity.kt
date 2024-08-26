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
import com.example.satu.utils.SnackbarUtils
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

        val sharedPrefTFRekning = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
        val targetRekeningNumber = sharedPrefTFRekning .getString("targetRekeningNumber", "")
        val nameTujuan = sharedPrefTFRekning .getString("namaRekeningTujuan", "")

        binding.tvName.text = nameTujuan
        binding.tvCardNumber.text = targetRekeningNumber
        binding.tvRekeningSumber.text = "Saver+ ($rekeningNumber)"
        binding.tvSaldo.text = "Saldo: IDR $formattedBalance"
        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferNowActivity, TransferAddNewRekeningActivity::class.java))
            finish()
        }
        btnNext.setOnClickListener {
            // Validate inputs
            val nominalText = etNominal.text.toString()
            val catatanText = etCatatan.text.toString()

            if (nominalText.isEmpty()) {
                showSnackBar("Inputan nominal wajib diisi")
                return@setOnClickListener
            }

            val nominal = nominalText.toDoubleOrNull()
            if (nominal == null) {
                showSnackBar( "Nominal harus berupa angka")
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("UserTransfer", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("nominal", nominalText)
                putString("catatan", catatanText)
                apply()
            }
            val intent = Intent(this@TransferNowActivity, TransferConfirmationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }

}