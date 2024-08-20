package com.example.satu.ui.activity.transfer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivityTransferNowBinding

class transferNow : AppCompatActivity() {
    private lateinit var binding: ActivityTransferNowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferNowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        setupRadioButtonListeners()

        // Retrieve the data passed from SelectTimeActivity
        val selectedDateTime = intent.getStringExtra("SELECTED_DATE_TIME")

        // Update the button text if date and time are available
        if (selectedDateTime != null) {
            binding.btnChoose.text = "Tanggal dan Waktu: $selectedDateTime"
            binding.btnChoose.visibility = View.VISIBLE
        } else {
            binding.btnChoose.visibility = View.GONE
        }
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@transferNow, transferRekBca::class.java))
        }
        btnNext.setOnClickListener {
            startActivity(Intent(this@transferNow, confirmationTransfer::class.java))
        }
        btnChoose.setOnClickListener {
            startActivity(Intent(this@transferNow, SelectTimeActivity::class.java))
        }
    }

    private fun setupRadioButtonListeners() = with(binding) {
        rbAturTanggal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Show the btnChoose button
                binding.btnChoose.visibility = View.VISIBLE
            } else {
                binding.btnChoose.visibility = View.GONE
            }
        }
        rbSekarang.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.btnChoose.visibility = View.GONE
            }
        }
    }
}
