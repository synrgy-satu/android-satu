package com.example.satu.ui.activities.qris

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.data.model.response.qris.DataQris
import com.example.satu.data.model.response.transfer.DataCardRekening
import com.example.satu.databinding.ActivityNominalQrisBinding
import com.example.satu.ui.activities.transfer.TransferNowActivity
import com.example.satu.ui.factory.QrisViewModelfactory
import com.example.satu.ui.factory.TransferViewModelfactory
import com.example.satu.ui.viewmodel.QrisViewModel
import com.example.satu.ui.viewmodel.TransferViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.Result
import com.example.satu.utils.SnackbarUtils
import java.text.NumberFormat
import java.util.Locale

class NominalQrisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNominalQrisBinding
    private val viewModel: QrisViewModel by viewModels {
       QrisViewModelfactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNominalQrisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPrefToken = getSharedPreferences("UserToken", Context.MODE_PRIVATE)
        val token = sharedPrefToken .getString("token", "")

        val barcodeValue = intent.getStringExtra("barcodeValue")
        if (barcodeValue != null && token != null) {
            viewModel.getDataQris(token, barcodeValue).observe(this@NominalQrisActivity) { result ->
                when (result) {
                    is Result.Loading -> ProgressDialogUtils.showProgressDialog(this@NominalQrisActivity)
                    is Result.Success -> result.data.data?.let { onLoginSuccess(it) }
                    is Result.Error -> onError(result.error)
                }
            }
            // binding.tvName.text = barcodeValue
            // Toast.makeText(this, "Received Barcode: $barcodeValue", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(this, "No barcode value received", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoginSuccess(data: DataQris) {
        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val rekeningNumber = sharedPref.getString("rekening_number", "")

        val balanceString = sharedPref.getString("balance", "")

        // Format balance
        val balance = balanceString?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedBalance = numberFormat.format(balance)

        binding.tvNominal.text = formattedBalance
        binding.tvName.text  = data.name
        binding.tvRekeningSumber.text = "Saver+ ($rekeningNumber)"
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