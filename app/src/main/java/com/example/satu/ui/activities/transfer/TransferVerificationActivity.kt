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
import com.example.satu.databinding.ActivityTransferVerificationBinding
import com.example.satu.utils.SnackbarUtils
import java.text.NumberFormat
import java.util.Locale

class TransferVerificationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferVerificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val debitedRekeningNumber = sharedPref.getString("rekening_number", "")
        val pin = sharedPref.getString("pin", "")


        val sharedPrefTF = getSharedPreferences("UserTransfer", Context.MODE_PRIVATE)
        val amount = sharedPrefTF.getString("nominal", "")
        val note= sharedPrefTF.getString("catatan", "")

        val sharedPrefTFRekning = getSharedPreferences("UserTransferRekening", Context.MODE_PRIVATE)
        val targetRekeningNumber = sharedPrefTFRekning .getString("targetRekeningNumber", "")

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding){
        topAppBar.setOnClickListener {
            startActivity(Intent(this@TransferVerificationActivity, TransferConfirmationActivity::class.java))
        }

        btnLanjut.setOnClickListener {
            val sharedPref = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
            val pin = sharedPref.getString("pin", "")
            val enteredPin = etVerifPin.text.toString()
            if (enteredPin == pin) {
                startActivity(Intent(this@TransferVerificationActivity, TransferReceiptActivity::class.java))
                finish()
            } else {
                showSnackBar("Pin salah")
            }
        }

    }

    private fun showSnackBar(message: String) {
        SnackbarUtils.showWithDismissAction(binding.root, message)
    }
}