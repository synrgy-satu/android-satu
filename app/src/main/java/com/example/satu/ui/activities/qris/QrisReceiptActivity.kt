package com.example.satu.ui.activities.qris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityPayWithCodeBinding
import com.example.satu.databinding.ActivityQrisReceiptBinding
import com.example.satu.ui.MainActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class QrisReceiptActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrisReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrisReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the data from the Intent
        val transferAmount = intent.getStringExtra("nominal")
        val referenceNumber = intent.getStringExtra("referenceNumber")
        val time = intent.getStringExtra("waktuTransaksi")
        val namaPenerima = intent.getStringExtra("namaPenerima")
        val namaPengirim = intent.getStringExtra("namaPengirim")
        val rekeningNumber = intent.getStringExtra("rekeningNumber")


        val nominal = transferAmount ?.toDoubleOrNull() ?: 0.0
        val numberFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
        numberFormat.maximumFractionDigits = 2
        val formattedNominal= numberFormat.format(nominal)


        // Set the data to the views using ViewBinding
        binding.tvIdrTransfer.text = "IDR $formattedNominal"
        binding.tvTime.text = time?.let { formatDate(it) }
        binding.tvName.text = namaPenerima ?: ""
        binding.tvDariRekening.text = "$rekeningNumber ($namaPengirim)"
        binding.tvNoRef.text = referenceNumber

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this@QrisReceiptActivity, MainActivity::class.java))
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