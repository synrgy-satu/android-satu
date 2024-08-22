package com.example.satu.ui.activities.qris

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityPayWithCodeBinding

class PayWithCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPayWithCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayWithCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() = with(binding) {
        topAppBar.setOnClickListener {
            startActivity(Intent(this@PayWithCodeActivity, QrisActivity::class.java))
        }

        btnCode.setOnClickListener {
            startActivity(Intent(this@PayWithCodeActivity, VerifikasiQrisActivity::class.java))
        }
    }
}