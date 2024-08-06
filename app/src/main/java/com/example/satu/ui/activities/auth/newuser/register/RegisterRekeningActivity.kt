package com.example.satu.ui.activities.auth.newuser.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityOnBoardingNewUserBinding
import com.example.satu.databinding.ActivityRegisterRekeningBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity
import com.example.satu.ui.activities.auth.newuser.onboarding.OnBoardingNewUserActivity
import com.example.satu.ui.activities.loader.LoaderRegisterRekeningActivity

class RegisterRekeningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterRekeningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnNext.setOnClickListener {
            startActivity(Intent(this@RegisterRekeningActivity, LoaderRegisterRekeningActivity::class.java))
        }
        topAppBar.setOnClickListener {
            startActivity(Intent(this@RegisterRekeningActivity, OnBoardingNewUserActivity::class.java))
        }
    }
}