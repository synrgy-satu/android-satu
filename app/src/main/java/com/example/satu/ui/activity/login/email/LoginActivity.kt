package com.example.satu.ui.activity.login.email

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satu.R
import com.example.satu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
    }
}