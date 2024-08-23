package com.example.satu.ui.activities.transfer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.data.model.response.transfer.DataTransfer
import com.example.satu.databinding.ActivityTransferConfirmationBinding
import com.example.satu.databinding.ActivityTransferVerificationBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginSuccessActivity
import com.example.satu.ui.factory.TransferViewModelfactory
import com.example.satu.ui.viewmodel.TransferViewModel
import com.example.satu.utils.ProgressDialogUtils
import com.example.satu.utils.Result
import com.example.satu.utils.SnackbarUtils
import java.text.NumberFormat
import java.util.Locale

class TransferVerificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferVerificationBinding
    private val viewModel: TransferViewModel by viewModels {
        TransferViewModelfactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferVerificationActivity, TransferConfirmationActivity::class.java))
        }

        btnLanjut.setOnClickListener {

            val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val pin = sharedPref.getString("pin", "")
            val debitedRekeningNumberString = sharedPref.getString("rekening_number", "")
            val debitedRekeningNumber: Long? = debitedRekeningNumberString?.toLongOrNull()

            val sharedPrefTFRekning = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
            val targetRekeningNumberString = sharedPrefTFRekning .getString("targetRekeningNumber", "")
            val targetRekeningNumber : Long? = targetRekeningNumberString?.toLongOrNull()
            val nameTujuan = sharedPrefTFRekning .getString("namaRekeningTujuan", "")

            val sharedPrefTF = getSharedPreferences("UserTransfer", Context.MODE_PRIVATE)
            val amountString = sharedPrefTF.getString("nominal", "")
            val amount : Long? = amountString?.toLongOrNull()
            val note= sharedPrefTF.getString("catatan", "")

            val sharedPrefToken = getSharedPreferences("UserToken", Context.MODE_PRIVATE)
            val token = sharedPrefToken .getString("token", "")

            val enteredPin = etVerifPin.text.toString()
            if (enteredPin == pin) {
                if (debitedRekeningNumber != null) {
                    if (targetRekeningNumber != null) {
                        if (amount != null) {
                            if (note != null) {
                                if (token != null) {
                                    viewModel.addTransfer(token, debitedRekeningNumber, targetRekeningNumber, amount, enteredPin, note).observe(this@TransferVerificationActivity) { result ->
                                        when (result) {
                                            is Result.Loading -> ProgressDialogUtils.showProgressDialog(this@TransferVerificationActivity)
                                            is Result.Success -> result.data.data?.let { onLoginSuccess(it) }
                                            is Result.Error -> onLoginError(result.error)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                showSnackBar("Pin salah")
            }
        }

    }

    private fun onLoginSuccess(data: DataTransfer) {
        ProgressDialogUtils.hideProgressDialog()

        val nominal = data.amount ?: 0
        val referenceNumber = data.referenceNumber ?: 0
        val waktuTransaksi = data.createdDate ?: ""
        val note = data.note ?: ""
        val namaPenerima = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
            .getString("namaRekeningTujuan", "") ?: ""
        val rekeningTujuan = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
            .getString("targetRekeningNumber", "") ?: ""

        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val fullName = sharedPref.getString("full_name", "")
        val debitedRekeningNumber = sharedPref.getString("rekening_number", "")


        val intent = Intent(this@TransferVerificationActivity, TransferReceiptActivity::class.java).apply {
            putExtra("nominal", nominal.toString())
            putExtra("waktu_transaksi", waktuTransaksi)
            putExtra("nama_penerima", namaPenerima)
            putExtra("rekening_tujuan", rekeningTujuan)
            putExtra("rekening_pengirim", debitedRekeningNumber)
            putExtra("nama_pengirim", fullName)
            putExtra("note", note)
            putExtra("referenceNumber", referenceNumber)
        }

        startActivity(intent)
        clearSharedPreferences()
        finish()
    }
    private fun onLoginError(errorMessage: String) {
        ProgressDialogUtils.hideProgressDialog()
        showSnackBar(errorMessage)
    }
    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }

    private fun clearSharedPreferences() {
        getSharedPreferences("UserTransfer", Context.MODE_PRIVATE).edit().clear().apply()

        getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE).edit().clear().apply()
    }
}