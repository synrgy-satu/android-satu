package com.example.satu.ui.activity.register.email

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityRegisterEmailBinding
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.activity.register.password.RegisterPasswordActivity

class RegisterEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextRegisterEmail.setOnClickListener {
            val intent = Intent(this, RegisterPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}