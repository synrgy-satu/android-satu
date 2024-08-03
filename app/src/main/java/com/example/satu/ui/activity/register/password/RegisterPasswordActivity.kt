package com.example.satu.ui.activity.register.password

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterEmailBinding
import com.example.satu.databinding.ActivityRegisterPasswordBinding
import com.example.satu.ui.activity.register.pin.PinRegisterActivity

class RegisterPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextRegisterPassword.setOnClickListener {
            val intent = Intent(this, PinRegisterActivity::class.java)
            startActivity(intent)
        }
    }
} 
