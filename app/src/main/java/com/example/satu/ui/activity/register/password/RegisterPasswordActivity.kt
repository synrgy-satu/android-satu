package com.example.satu.ui.activity.register.password

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
