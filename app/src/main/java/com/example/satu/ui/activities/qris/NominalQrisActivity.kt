package com.example.satu.ui.activities.qris

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityNominalQrisBinding

class NominalQrisActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNominalQrisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNominalQrisBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val barcodeValue = intent.getStringExtra("barcodeValue")
        if (barcodeValue != null) {
            binding.tvName.text = barcodeValue
            Toast.makeText(this, "Received Barcode: $barcodeValue", Toast.LENGTH_SHORT).show()
          } else {
            Toast.makeText(this, "No barcode value received", Toast.LENGTH_SHORT).show()
        }
    }
}