package com.example.satu.ui.activities.qris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.data.model.response.qris.DataQris
import com.example.satu.databinding.ActivityNominalQrisBinding
import com.example.satu.ui.factory.QrisViewModelfactory
import com.example.satu.ui.viewmodel.QrisViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.SnackbarUtils
import java.text.NumberFormat
import java.util.Locale

class NominalQrisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNominalQrisBinding
    private val viewModel: QrisViewModel by viewModels {
       QrisViewModelfactory.getInstance(application)
    }

    private lateinit var barcodeValue: String
    private lateinit var namaPenerima: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNominalQrisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefToken = getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val token = sharedPrefToken .getString("token", "")

        barcodeValue = intent.getStringExtra("barcodeValue").toString()
        if (barcodeValue != null && token != null) {
            viewModel.getDataQris("Bearer $token", barcodeValue).observe(this@NominalQrisActivity) { result ->
                when (result) {
                    is com.example.common.Result.Loading -> ProgressDialogUtils.showProgressDialog(this@NominalQrisActivity)
                    is com.example.common.Result.Success -> result.data.data?.let { onLoginSuccess(it) }
                    is com.example.common.Result.Error -> onError(result.error)
                }
            }
            // binding.tvName.text = barcodeValue
            // Toast.makeText(this, "Received Barcode: $barcodeValue", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(this, "No barcode value received", Toast.LENGTH_SHORT).show()
        }
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnBayar.setOnClickListener {
            val nominalText = binding.etNominal.text.toString().trim()
            if (nominalText.isEmpty()) {
                Toast.makeText(this, "Harap input nominal transfer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nominal = nominalText.toDoubleOrNull()
            if (nominal == null || nominal < 10000) {
                Toast.makeText(this, "Nominal harus minimal IDR 10.000", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val rekeningNumber = sharedPref.getString("rekening_number", "")

            // Jika validasi berhasil, pindah ke halaman TransferNowActivity
            val intent = Intent(this, QrisVerificationActivity::class.java).apply {
                putExtra("amount", nominalText)
                putExtra("namaPenerima", namaPenerima)
                putExtra("targetQris", barcodeValue) // Make sure barcodeValue is accessible here
                putExtra("debitedRekeningNumber", rekeningNumber)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun onLoginSuccess(data: DataQris) {
        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val rekeningNumber = sharedPref.getString("rekening_number", "")
        val fullName = sharedPref.getString("full_name", "")
        val balanceString = sharedPref.getString("balance", "")
        namaPenerima = data.name.toString()
        // Format balance
        val balance = balanceString?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedBalance = numberFormat.format(balance)

        binding.tvSaldo.text = "Saldo: IDR $formattedBalance"
        binding.tvName.text  = data.name
        binding.tvRekeningSumber.text = "Saver+ ($rekeningNumber) - $fullName"
        ProgressDialogUtils.hideProgressDialog()
    }

    private fun onError(errorMessage: String) {
        Log.e("erornih", errorMessage)
        binding.tvName.text  = errorMessage
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }

    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }
}