package com.example.satu.ui.activities.auth.newuser.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satu.R
import com.example.satu.databinding.ActivityOnBoardingNewUserBinding
import com.example.satu.ui.activities.auth.newuser.login.LoginEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterEmailActivity
import com.example.satu.ui.activities.auth.newuser.register.RegisterRekeningActivity

class OnBoardingNewUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingNewUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupClickListeners()
    }
    private fun setupClickListeners() = with(binding) {
        btnLoginOnboarding.setOnClickListener {
            startActivity(Intent(this@OnBoardingNewUserActivity, LoginEmailActivity::class.java))
        }
        btnRegisOnboarding.setOnClickListener {
            startActivity(Intent(this@OnBoardingNewUserActivity, RegisterRekeningActivity::class.java))
        }
    }
}