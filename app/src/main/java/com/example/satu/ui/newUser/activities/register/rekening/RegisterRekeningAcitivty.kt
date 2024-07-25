package com.example.satu.ui.newUser.activities.register.rekening


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.newUser.activities.register.email.RegisterEmailActivity


class RegisterRekeningAcitivty : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterRekeningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextRegisterRek.setOnClickListener {
            val intent = Intent(this, RegisterEmailActivity::class.java)
            startActivity(intent)
        }
    }
}
