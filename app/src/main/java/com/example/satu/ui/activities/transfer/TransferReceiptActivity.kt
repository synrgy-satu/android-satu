package com.example.satu.ui.activities.transfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityTransferConfirmationBinding
import com.example.satu.databinding.ActivityTransferReceiptBinding
import com.example.satu.ui.MainActivity
import com.example.satu.ui.activities.auth.newuser.login.LoginSuccessActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class TransferReceiptActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTransferReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val time = intent.getStringExtra("waktu_transaksi")
        val receiverName = intent.getStringExtra("nama_penerima")
        val rekeningTujuan = intent.getStringExtra("rekening_tujuan")
        val note = intent.getStringExtra("note")
        val referenceNumber = intent.getStringExtra("referenceNumber")
        val transferAmount = intent.getStringExtra("nominal")
        val nama_pengirim = intent.getStringExtra("nama_pengirim")
        val rekening_pengirim = intent.getStringExtra("rekening_pengirim")

        val nominal = transferAmount ?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedNominal= numberFormat.format(nominal)

        binding.tvTime.text = time?.let { formatDate(it) }
        binding.tvIdrTransfer.text = "IDR $formattedNominal"
        binding.tvName.text = receiverName
        binding.tvRekening.text = rekeningTujuan
        binding.tvCatatan.text = note
        binding.tvNoRef.text = referenceNumber
        binding.tvDariRekening.text = "$rekening_pengirim ($nama_pengirim)"

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@TransferReceiptActivity, MainActivity::class.java))
            finish()
        }
    }

    fun formatDate(isoDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM yyyy HH:mm:ss", Locale("id", "ID"))
        val date = inputFormat.parse(isoDate)
        return outputFormat.format(date ?: "")
    }
}